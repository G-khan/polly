package com.gokhana.polly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PollyApplication

fun main(args: Array<String>) {
    runApplication<PollyApplication>(*args)
}