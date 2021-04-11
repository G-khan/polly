package com.gokhana.polly.model.entity

import com.gokhana.polly.model.entity.audit.UserDateAudit
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "polls")
data class Poll(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Size(max = 140)
    @NotBlank
    var question: String,

    @OneToMany(mappedBy = "poll", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    @Size(min = 2, max = 6)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    var choices: List<Choice> = arrayListOf(),

    @NotNull
    var expirationDateTime: Instant
) : UserDateAudit()