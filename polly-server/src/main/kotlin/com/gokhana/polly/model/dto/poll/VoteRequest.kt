package com.gokhana.polly.model.dto.poll

import javax.validation.constraints.NotNull


class VoteRequest {
    var choiceId: @NotNull Long? = null
}