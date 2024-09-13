package com.alshareef.security.service;

import com.alshareef.security.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class EmailService {

    @Value("${email.sender}")
    private String emailSender;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    public void sendHtmlEmail(User user, String subject, String htmlBody) throws MessagingException, IOException {

        // Load email template from resources
        String template = new String(Files.readAllBytes(
                Paths.get(resourceLoader.getResource("classpath:templates/welcome-email.html").getURI())
        ));

        // Replace placeholders in the template
        String content = template
                .replace("{{username}}", user.getUsername())
                .replace("{{role}}", user.getRole());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(content, true);  // "true" means HTML content
        helper.setFrom(emailSender);

        mailSender.send(message);
    }
}
