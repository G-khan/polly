package com.gokhana.polly.controller



import com.gokhana.polly.model.dto.BaseResponse
import com.gokhana.polly.model.dto.poll.PagedResponse
import com.gokhana.polly.model.dto.poll.PollRequest
import com.gokhana.polly.model.dto.poll.PollResponse
import com.gokhana.polly.model.dto.poll.VoteRequest
import com.gokhana.polly.model.entity.Poll
import com.gokhana.polly.security.model.CurrentUser
import com.gokhana.polly.security.model.UserPrincipal
import com.gokhana.polly.service.PollService
import com.gokhana.polly.util.PollyConstants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid


@RestController
@RequestMapping("/api/polls")
class PollController {

    companion object {
        private val logger = LoggerFactory.getLogger(PollController::class.java)
    }

    @Autowired
    lateinit var pollService: PollService

    @GetMapping
    fun getPolls(
        @CurrentUser currentUser: UserPrincipal?,
        @RequestParam(value = "page", defaultValue = PollyConstants.DEFAULT_PAGE_NUMBER) page: Int,
        @RequestParam(value = "size", defaultValue = PollyConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<PollResponse> {
        return pollService.getAllPolls(currentUser, page, size)
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createPoll(@RequestBody pollRequest: @Valid PollRequest): ResponseEntity<*> {
        val poll: Poll = pollService.createPoll(pollRequest)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{pollId}")
            .buildAndExpand(poll.id).toUri()
        return ResponseEntity.created(location)
            .body<Any>(BaseResponse(true, "Poll Created Successfully"))
    }

    @GetMapping("/{pollId}")
    fun getPollById(
        @CurrentUser currentUser: UserPrincipal?,
        @PathVariable pollId: Long?
    ): PollResponse {
        return pollService.getPollById(pollId, currentUser)
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    fun castVote(
        @CurrentUser currentUser: UserPrincipal,
        @PathVariable pollId: Long,
        @RequestBody voteRequest: @Valid VoteRequest
    ): PollResponse {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser)
    }


}