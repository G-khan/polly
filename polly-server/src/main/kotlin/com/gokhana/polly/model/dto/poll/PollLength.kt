package com.gokhana.polly.model.dto.poll

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull


class PollLength {
    var days: @NotNull @Max(7) Int? = null
    var hours: @NotNull @Max(23) Int? = null
}