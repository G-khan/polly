package com.gokhana.polly.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(private val resource: String, private val fieldName: String, private val fieldValue: Any) :
    RuntimeException("$resource not found with $fieldName : '$fieldValue'")