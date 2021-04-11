package com.gokhana.polly.config

import com.gokhana.polly.security.model.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


@Configuration
@EnableJpaAuditing
class AuditingConfig {
    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return SpringSecurityAuditAwareImpl()
    }
}

internal class SpringSecurityAuditAwareImpl : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        if (authentication == null ||
            !authentication.isAuthenticated() ||
            authentication is AnonymousAuthenticationToken
        ) {
            return Optional.empty()
        }
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        return Optional.ofNullable(userPrincipal.id)
    }
}