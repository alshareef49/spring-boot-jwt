package com.alshareef.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/v1/api/test"})
public class TestController {
    public TestController() {
    }

    @GetMapping({"/hello"})
    public ResponseEntity<?> signup() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Hello from authenticated user.......");
    }
}
