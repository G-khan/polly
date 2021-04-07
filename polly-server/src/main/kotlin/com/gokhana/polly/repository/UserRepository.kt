package com.gokhana.polly.repository

import com.gokhana.polly.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User?, Long?> {
    fun findByEmail(email: String?): Optional<User?>?
    fun findByUsernameOrEmail(username: String?, email: String?): Optional<User?>?
    fun findByIdIn(userIds: List<Long?>?): List<User?>?
    fun findByUsername(username: String?): Optional<User?>?
    fun existsByUsername(username: String?): Boolean?
    fun existsByEmail(email: String?): Boolean?
}