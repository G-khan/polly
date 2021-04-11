package com.gokhana.polly.model.dto.poll

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant


data class PollResponse (
    var id: Long? = null,
    var question: String? = null,
    var choices: List<ChoiceResponse>? = null,
    var createdBy: UserSummary? = null,
    var creationDateTime: Instant? = null,
    var expirationDateTime: Instant? = null,
    var expired: Boolean? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var selectedChoice: Long? = null,
    var totalVotes: Long? = null
)