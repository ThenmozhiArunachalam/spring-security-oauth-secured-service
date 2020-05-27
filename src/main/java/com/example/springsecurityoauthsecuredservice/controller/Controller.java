package com.example.springsecurityoauthsecuredservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(value = "api/v1/test")
    public String test(Authentication user) {
        System.out.println("is Authenticated: " + user.isAuthenticated());
        return "Hello";
    }

}
