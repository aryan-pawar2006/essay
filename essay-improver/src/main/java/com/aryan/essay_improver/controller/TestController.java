package com.aryan.essay_improver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Essay Improver 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "Backend is working 🚀";
    }
}