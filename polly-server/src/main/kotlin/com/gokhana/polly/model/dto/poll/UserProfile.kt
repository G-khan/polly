package com.gokhana.polly.model.dto.poll

import java.time.Instant


data class UserProfile(
    var id: Long,
    var username: String,
    var name: String,
    var joinedAt: Instant,
    var pollCount: Long,
    var voteCount: Long
)