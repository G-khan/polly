package com.gokhana.polly.model.entity.user

import com.gokhana.polly.enum.RoleType
import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Enumerated(EnumType.STRING)
    @NaturalId
    var type: RoleType
)


