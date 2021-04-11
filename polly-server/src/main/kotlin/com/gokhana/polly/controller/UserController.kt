package com.gokhana.polly.controller

import com.gokhana.polly.exception.NotFoundException
import com.gokhana.polly.model.dto.poll.*
import com.gokhana.polly.model.entity.user.User
import com.gokhana.polly.repository.PollRepository
import com.gokhana.polly.repository.UserRepository
import com.gokhana.polly.repository.VoteRepository
import com.gokhana.polly.security.model.CurrentUser
import com.gokhana.polly.security.model.UserPrincipal
import com.gokhana.polly.service.PollService
import com.gokhana.polly.util.PollyConstants
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotBlank


@RestController
@RequestMapping("/api")
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var pollRepository: PollRepository

    @Autowired
    lateinit var voteRepository: VoteRepository

    @Autowired
    lateinit var pollService: PollService

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserSummary {
        return UserSummary(currentUser.id, currentUser.username, currentUser.name)
    }

    @GetMapping("/user/checkUsernameAvailability")
    fun checkUsernameAvailability(@RequestParam(value = "username") username: String?): UserIdentityAvailability {
        val isAvailable = !userRepository.existsByUsername(username)
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/user/checkEmailAvailability")
    fun checkEmailAvailability(@RequestParam(value = "email") email: String?): UserIdentityAvailability {
        val isAvailable = !userRepository.existsByEmail(email)
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/users/{username}")
    fun getUserProfile(@PathVariable(value = "username") @NotBlank username: String): UserProfile {
        val user: User = userRepository.findByUsername(username) ?: throw NotFoundException("User", "username", username)
        val pollCount = pollRepository.countByCreatedBy(user.id)
        val voteCount = voteRepository.countByUserId(user.id)
        return UserProfile(user.id, user.username, user.name, user.createdAt, pollCount, voteCount)
    }

    @GetMapping("/users/{username}/polls")
    fun getPollsCreatedBy(
        @PathVariable(value = "username") username: String?,
        @CurrentUser currentUser: UserPrincipal?,
        @RequestParam(value = "page", defaultValue = PollyConstants.DEFAULT_PAGE_NUMBER) page: Int,
        @RequestParam(value = "size", defaultValue = PollyConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<PollResponse> {
        return pollService.getPollsCreatedBy(username, currentUser, page, size)
    }

    @GetMapping("/users/{username}/votes")
    fun getPollsVotedBy(
        @PathVariable(value = "username") username: String?,
        @CurrentUser currentUser: UserPrincipal?,
        @RequestParam(value = "page", defaultValue = PollyConstants.DEFAULT_PAGE_NUMBER) page: Int,
        @RequestParam(value = "size", defaultValue = PollyConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<PollResponse> {
        return pollService.getPollsVotedBy(username, currentUser, page, size)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}