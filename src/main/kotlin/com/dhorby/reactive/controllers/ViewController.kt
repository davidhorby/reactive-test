package com.dhorby.reactive.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class ViewController {

    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): ModelAndView {
        val modelAndView = ModelAndView("hello")
        modelAndView.addObject("user", name)
        return modelAndView
    }
}