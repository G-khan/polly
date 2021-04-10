package com.gokhana.polly.model.entity

import com.gokhana.polly.model.entity.user.User
import javax.persistence.*


@Entity
@Table(name = "votes", uniqueConstraints = [UniqueConstraint(columnNames = ["poll_id", "user_id"])])
data class Vote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    var poll: Poll,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_id", nullable = false)
    var choice: Choice,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)

