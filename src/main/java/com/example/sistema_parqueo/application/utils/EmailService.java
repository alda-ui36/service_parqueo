package com.example.sistema_parqueo.application.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    public void sendSimpleMessage(String to, String subject, String htmlContent){
        try{
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message,true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent,true);
            helper.setFrom(new InternetAddress(remitente, "Sistema parqueo"));
            mailSender.send(message);
        }catch (MessagingException | UnsupportedEncodingException e){
            throw  new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);

        }
    }

}
