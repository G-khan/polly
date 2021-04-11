package com.gokhana.polly.model.dto.poll

import javax.validation.constraints.NotNull


data class VoteRequest (
    var choiceId: @NotNull Long
)