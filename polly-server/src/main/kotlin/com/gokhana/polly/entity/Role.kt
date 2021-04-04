package com.gokhana.polly.entity

import com.gokhana.polly.enum.RoleType
import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Enumerated(EnumType.STRING)
    @NaturalId
    var roleType: RoleType
    )


