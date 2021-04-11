package com.gokhana.polly.model.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@Entity
@Table(name = "choices")
data class Choice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: @NotBlank @Size(max = 40) Long = 0,

    @NotBlank
    @Size(max = 40)
    var text:String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    var poll: Poll
)