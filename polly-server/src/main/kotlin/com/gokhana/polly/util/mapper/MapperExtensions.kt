package com.gokhana.polly.util.mapper

import com.gokhana.polly.model.dto.poll.ChoiceResponse
import com.gokhana.polly.model.dto.poll.PollResponse
import com.gokhana.polly.model.dto.poll.UserSummary
import com.gokhana.polly.model.entity.Poll
import com.gokhana.polly.model.entity.user.User
import java.time.Instant
import java.util.stream.Collectors


object MapperExtensions {


    fun mapPollToPollResponse(
        poll: Poll,
        choiceVotesMap: Map<Long, Long>,
        creator: User,
        userVote: Long?
    ): PollResponse {
        val pollResponse = PollResponse()
        pollResponse.id = poll.id
        pollResponse.question = poll.question
        pollResponse.creationDateTime = poll.createdAt
        pollResponse.expirationDateTime = poll.expirationDateTime
        val now = Instant.now()
        pollResponse.expired = poll.expirationDateTime.isBefore(now)
        val choiceResponses: List<ChoiceResponse> = poll.choices.stream().map { choice ->
            val choiceResponse = ChoiceResponse()
            choiceResponse.id = choice.id
            choiceResponse.text = choice.text
            if (choiceVotesMap.containsKey(choice.id)) {
                choiceResponse.voteCount = choiceVotesMap[choice.id]!!
            } else {
                choiceResponse.voteCount = 0
            }
            choiceResponse
        }.collect(Collectors.toList())
        pollResponse.choices = choiceResponses
        val creatorSummary = UserSummary(creator.id, creator.username, creator.name)
        pollResponse.createdBy = creatorSummary
        if (userVote != null) {
            pollResponse.selectedChoice = userVote
        }
        val totalVotes = pollResponse.choices!!.stream().mapToLong(ChoiceResponse::voteCount).sum()
        pollResponse.totalVotes = totalVotes
        return pollResponse
    }



}