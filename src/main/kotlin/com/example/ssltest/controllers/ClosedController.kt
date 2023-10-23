package com.example.ssltest.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class ClosedController {
    @GetMapping("/closed")
    fun closed() : String {
        return "must be placed on internal area"
    }
}