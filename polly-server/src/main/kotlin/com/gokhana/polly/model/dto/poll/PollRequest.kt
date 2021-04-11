package com.gokhana.polly.model.dto.poll

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


class PollRequest {
    var question: @NotBlank @Size(max = 140) String? = null
    var choices: @NotNull @Size(min = 2, max = 6) @Valid MutableList<ChoiceRequest>? = null
    var pollLength: @NotNull @Valid PollLength? = null
}