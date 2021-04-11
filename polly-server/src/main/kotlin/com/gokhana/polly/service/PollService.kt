package com.gokhana.polly.service

import com.gokhana.polly.exception.BadRequestException
import com.gokhana.polly.exception.NotFoundException
import com.gokhana.polly.model.ChoiceVoteCount
import com.gokhana.polly.model.dto.poll.*
import com.gokhana.polly.model.entity.Choice
import com.gokhana.polly.model.entity.Poll
import com.gokhana.polly.model.entity.Vote
import com.gokhana.polly.model.entity.user.User
import com.gokhana.polly.repository.PollRepository
import com.gokhana.polly.repository.UserRepository
import com.gokhana.polly.repository.VoteRepository
import com.gokhana.polly.security.model.UserPrincipal
import com.gokhana.polly.util.PollyConstants
import com.gokhana.polly.util.mapper.MapperExtensions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.Collectors


@Service
class PollService {
    @Autowired
    lateinit var pollRepository: PollRepository

    @Autowired
    lateinit var voteRepository: VoteRepository

    @Autowired
    lateinit var userRepository: UserRepository

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PollService::class.java)
    }

    fun getAllPolls(currentUser: UserPrincipal?, page: Int, size: Int): PagedResponse<PollResponse> {
        validatePageNumberAndSize(page, size)

        // Retrieve Polls
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        val polls: Page<Poll> = pollRepository.findAll(pageable)
        if (polls.numberOfElements == 0) {
            return PagedResponse(
                Collections.emptyList(), polls.number,
                polls.size, polls.totalElements, polls.totalPages, polls.isLast
            )
        }

        // Map Polls to PollResponses containing vote counts and poll creator details
        val pollIds: List<Long> = polls.map(Poll::id).content as List<Long>
        val choiceVoteCountMap = getChoiceVoteCountMap(pollIds)
        val pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds)
        val creatorMap: Map<Long, User> = getPollCreatorMap(polls.content)
        val pollResponses: List<PollResponse> = polls.map { poll ->
            MapperExtensions.mapPollToPollResponse(
                poll,
                choiceVoteCountMap,
                creatorMap[poll.createdBy]!!,
                pollUserVoteMap?.getOrDefault(poll.id, null)
            )
        }.getContent()
        return PagedResponse(
            pollResponses, polls.getNumber(),
            polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast()
        )
    }

    fun getPollsCreatedBy(
        username: String?,
        currentUser: UserPrincipal?,
        page: Int,
        size: Int
    ): PagedResponse<PollResponse> {
        validatePageNumberAndSize(page, size)
        val user: User = userRepository.findByUsername(username)
            ?: throw NotFoundException("User", "username", username!!)

        // Retrieve all polls created by the given username
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        val polls: Page<Poll> = pollRepository.findByCreatedBy(user.id, pageable)
        if (polls.numberOfElements == 0) {
            return PagedResponse(
                Collections.emptyList(), polls.number,
                polls.size, polls.totalElements, polls.totalPages, polls.isLast
            )
        }

        // Map Polls to PollResponses containing vote counts and poll creator details

        val pollIds: List<Long> = polls.map(Poll::id).content as List<Long>
        val choiceVoteCountMap = getChoiceVoteCountMap(pollIds)
        val pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds)
        val pollResponses: List<PollResponse> = polls.map { poll ->
            MapperExtensions.mapPollToPollResponse(
                poll,
                choiceVoteCountMap,
                user,
                pollUserVoteMap?.getOrDefault(poll?.id, null)
            )
        }.getContent()
        return PagedResponse(
            pollResponses, polls.getNumber(),
            polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast()
        )
    }

    fun getPollsVotedBy(
        username: String?,
        currentUser: UserPrincipal?,
        page: Int,
        size: Int
    ): PagedResponse<PollResponse> {
        validatePageNumberAndSize(page, size)
        val user: User = userRepository!!.findByUsername(username)
            ?: throw NotFoundException("User", "username", username!!)

        // Retrieve all pollIds in which the given username has voted
        val pageable: Pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        val userVotedPollIds: Page<Long?> = voteRepository!!.findVotedPollIdsByUserId(user.id, pageable)
        if (userVotedPollIds.getNumberOfElements() === 0) {
            return PagedResponse(
                Collections.emptyList(), userVotedPollIds.getNumber(),
                userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(),
                userVotedPollIds.getTotalPages(), userVotedPollIds.isLast()
            )
        }

        // Retrieve all poll details from the voted pollIds.
        val pollIds: List<Long> = userVotedPollIds.content as List<Long>
        val sort: Sort = Sort.by(Sort.Direction.DESC, "createdAt")
        val polls: List<Poll> = pollRepository.findByIdIn(pollIds, sort)

        // Map Polls to PollResponses containing vote counts and poll creator details
        val choiceVoteCountMap = getChoiceVoteCountMap(pollIds)
        val pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds)
        val creatorMap: Map<Long, User> = getPollCreatorMap(polls)
        val pollResponses: List<PollResponse> = polls.stream().map { poll: Poll ->
            MapperExtensions.mapPollToPollResponse(
                poll,
                choiceVoteCountMap,
                creatorMap[poll.createdBy]!!,
                pollUserVoteMap?.getOrDefault(poll.id, null)
            )
        }.collect(Collectors.toList())
        return PagedResponse(
            pollResponses,
            userVotedPollIds.number,
            userVotedPollIds.size,
            userVotedPollIds.totalElements,
            userVotedPollIds.totalPages,
            userVotedPollIds.isLast
        )
    }

    fun createPoll(pollRequest: PollRequest): Poll {
        val expirationDateTime = Instant.now().plus(Duration.ofDays(pollRequest.pollLength!!.days))
            .plus(Duration.ofHours(pollRequest.pollLength!!.hours))
        val poll = Poll(question = pollRequest.question, expirationDateTime = expirationDateTime)
        pollRequest.choices!!.forEach(Consumer { choiceRequest: ChoiceRequest ->

        })
        return pollRepository.save(poll)
    }

    fun getPollById(pollId: Long?, currentUser: UserPrincipal?): PollResponse {
        val poll: Poll = pollRepository.findById(pollId!!).orElseThrow(
            Supplier<RuntimeException> { NotFoundException("Poll", "id", pollId) })

        // Retrieve Vote Counts of every choice belonging to the current poll
        val votes = voteRepository.countByPollIdGroupByChoiceId(pollId)
        val choiceVotesMap = votes.stream()
            .collect(Collectors.toMap(ChoiceVoteCount::choiceId, ChoiceVoteCount::voteCount))

        // Retrieve poll creator details
        val creator: User = userRepository.findById(poll.createdBy)
            .orElseThrow(Supplier<RuntimeException> {
                NotFoundException(
                    "User",
                    "id",
                    poll.createdBy
                )
            })

        // Retrieve vote done by logged in user
        var userVote: Vote? = null
        if (currentUser != null) {
            userVote = voteRepository.findByUserIdAndPollId(currentUser.id, pollId)
        }
        return MapperExtensions.mapPollToPollResponse(
            poll, choiceVotesMap,
            creator, userVote?.choice?.id
        )
    }

    fun castVoteAndGetUpdatedPoll(pollId: Long?, voteRequest: VoteRequest, currentUser: UserPrincipal): PollResponse {
        val poll: Poll = pollRepository.findById(pollId!!)
            .orElseThrow {
                NotFoundException(
                    "Poll",
                    "id",
                    pollId
                )
            }
        if (poll.expirationDateTime.isBefore(Instant.now())) {
            throw BadRequestException("Sorry! This Poll has already expired")
        }
        val user: User = userRepository.getOne(currentUser.id)
        val selectedChoice: Choice = poll.choices.stream()
            .filter { (id) ->
                id == voteRequest.choiceId
            }
            .findFirst().orElseThrow { NotFoundException("Choice", "id", voteRequest.choiceId) }

        var vote = Vote(
            poll = poll,
            user = user,
            choice = selectedChoice
        )
        vote.poll = poll
        vote.user = user
        vote.choice = selectedChoice
        vote = try {
            voteRepository.save(vote)
        } catch (ex: DataIntegrityViolationException) {
            logger.info("User $currentUser.id has already voted in Poll $pollId")
            throw BadRequestException("Sorry! You have already cast your vote in this poll")
        }

        //-- Vote Saved, Return the updated Poll Response now --

        // Retrieve Vote Counts of every choice belonging to the current poll
        val votes = voteRepository.countByPollIdGroupByChoiceId(pollId)
        val choiceVotesMap = votes.stream()
            .collect(Collectors.toMap(ChoiceVoteCount::choiceId, ChoiceVoteCount::voteCount))

        // Retrieve poll creator details
        val creator: User = userRepository.findById(poll.createdBy)
            .orElseThrow(Supplier<RuntimeException> {
                NotFoundException(
                    "User",
                    "id",
                    poll.createdBy
                )
            })
        return MapperExtensions.mapPollToPollResponse(poll, choiceVotesMap, creator, vote.choice.id)
    }

    private fun validatePageNumberAndSize(page: Int, size: Int) {
        if (page < 0) {
            throw BadRequestException("Page number cannot be less than zero.")
        }
        if (size > PollyConstants.MAX_PAGE_SIZE) {
            throw BadRequestException("Page size must not be greater than " + PollyConstants.MAX_PAGE_SIZE)
        }
    }

    private fun getChoiceVoteCountMap(pollIds: List<Long>): Map<Long, Long> {
        // Retrieve Vote Counts of every Choice belonging to the given pollIds
        val votes: List<ChoiceVoteCount> = voteRepository.countByPollIdInGroupByChoiceId(pollIds)
        return votes.stream()
            .collect(Collectors.toMap(ChoiceVoteCount::choiceId, ChoiceVoteCount::voteCount))
    }

    private fun getPollUserVoteMap(currentUser: UserPrincipal?, pollIds: List<Long>): Map<Long, Long?>? {
        // Retrieve Votes done by the logged in user to the given pollIds
        var pollUserVoteMap: Map<Long, Long> = mutableMapOf()
        if (currentUser != null) {
            val userVotes = voteRepository.findByUserIdAndPollIdIn(currentUser.id, pollIds)
            pollUserVoteMap = userVotes.stream()
                .collect(
                    Collectors.toMap(
                        { (_, poll) -> poll.id },
                        { (_, _, choice) -> choice.id })
                )
        }
        return pollUserVoteMap
    }

    fun getPollCreatorMap(polls: List<Poll>): Map<Long, User> {
        // Get Poll Creator details of the given list of polls
        val creatorIds = polls.stream()
            .map(Poll::createdBy)
            .distinct()
            .collect(Collectors.toList())
        val creators: List<User> = userRepository.findByIdIn(creatorIds)
        return creators.stream()
            .collect(Collectors.toMap(User::id, Function.identity()))
    }


}