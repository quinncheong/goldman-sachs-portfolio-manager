package com.is442g1t4.gpa.auth.email;

import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendVerificationEmail(String name, String email, String token)
            throws MessagingException {
        Context context = new Context();
        String urlString = "http://localhost:3000/register/verify?token=" + token;
        context.setVariables(Map.of("name", name, "url", urlString));
        String messageText = templateEngine.process("emailtemplate", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("is442g1t4@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(messageText, true);
        // mimeMessageHelper.setText("hi",true);
        mimeMessageHelper.setSubject("NEW USER ACCOUNT VERIFICATION");

        javaMailSender.send(mimeMessage);

        System.out.println("Message sent");
    }

    public void sendForgetPasswordEmail(String name, String email, String token)
            throws MessagingException {
        Context context = new Context();
        String urlString = "http://localhost:3000/forget-password/verify?token=" + token;
        context.setVariables(Map.of("name", name, "url", urlString));
        String messageText = templateEngine.process("forgotpasswordtemplate", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("is442g1t4@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(messageText, true);
        // mimeMessageHelper.setText("hi",true);
        mimeMessageHelper.setSubject("FORGET PASSWORD");

        javaMailSender.send(mimeMessage);

        System.out.println("Message sent");
    }
}
