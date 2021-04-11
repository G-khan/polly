package com.gokhana.polly.model.dto.poll

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


class ChoiceRequest {
    var text: @NotBlank @Size(max = 40) String? = null
}