package com.gokhana.polly.repository

import com.gokhana.polly.entity.Role
import com.gokhana.polly.enum.RoleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RoleRepository : JpaRepository<Role?, Long> {
    fun findByType(roleType: RoleType): Role?
}