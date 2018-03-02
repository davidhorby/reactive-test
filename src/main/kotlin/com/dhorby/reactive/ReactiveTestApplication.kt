package com.dhorby.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveTestApplication

fun main(args: Array<String>) {
    runApplication<ReactiveTestApplication>(*args)
}
