package com.gokhana.polly.security.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gokhana.polly.model.entity.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors


data class UserPrincipal(
    internal var id: Long,
    private var name: String,
    private var username: String,
    @field:JsonIgnore var email: String,
    @field:JsonIgnore private var password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as UserPrincipal
        return id == that.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities: List<GrantedAuthority> = user.roles.stream().map { role ->
                SimpleGrantedAuthority(
                    role.type.name
                )
            }.collect(Collectors.toList())
            return UserPrincipal(
                user.id,
                user.name,
                user.username,
                user.email,
                user.password,
                authorities
            )
        }
    }
}