package com.example.ssltest.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.security.cert.X509Certificate

@RestController
@RequestMapping("")
class OpenController {
    @GetMapping("/open")
    fun open(principal: Principal) : String {
        return "to demostrate external area" + principal.toString()
    }
    @GetMapping("/external")
    fun external() : String {
        return "to demostrate external area"
    }
}