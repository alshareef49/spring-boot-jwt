package com.alshareef.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @GetMapping("/dashboard")
    public String authorDashboard() {
        return "Author Dashboard";
    }
}