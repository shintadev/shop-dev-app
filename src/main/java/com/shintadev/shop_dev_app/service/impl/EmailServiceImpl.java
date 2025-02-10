package com.shintadev.shop_dev_app.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;

  @Override
  public void sendHtmlEmail(String to, String subject, String htmlBody) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlBody, true);

      javaMailSender.send(message);
      log.info("Email sent to {}", to);
    } catch (Exception e) {
      e.printStackTrace();
      // throw new RuntimeException("Failed to send email");
    }
  }

}
