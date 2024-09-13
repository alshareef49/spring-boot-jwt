package com.alshareef.security.controller;

import com.alshareef.security.dto.AuthRequest;
import com.alshareef.security.dto.AuthResponse;
import com.alshareef.security.entity.User;
import com.alshareef.security.repository.UserRepository;
import com.alshareef.security.service.CustomUserDetailsService;
import com.alshareef.security.service.EmailService;
import com.alshareef.security.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping({"/v1/api/auth"})
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public AuthController() {
    }

    @PostMapping({"/login"})
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException var4) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping({"/signup"})
    public ResponseEntity<?> signup(@RequestBody User user) throws MessagingException, IOException {
        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("USER");
        } else {
            user.setRole(user.getRole());
        }
        this.userRepository.save(user);
        String subject = "Welcome to Our Application!";
        String body = "Hi " + user.getUsername() + ",\n\nThank you for signing up! We're glad to have you.";
        emailService.sendHtmlEmail(user, subject, body);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
