package com.alshareef.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviewer")
public class ReviewerController {

    @GetMapping("/dashboard")
    public String editorDashboard() {
        return "Editor Dashboard";
    }
}
