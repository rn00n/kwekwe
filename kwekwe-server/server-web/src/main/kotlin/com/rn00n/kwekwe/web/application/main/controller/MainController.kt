package com.rn00n.kwekwe.web.application.main.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping
    fun index(): String {
        return "index"
    }

    @GetMapping("/error")
    fun error(): String {
        return "error"
    }

}
