package com.shintadev.shop_dev_app.service;

public interface EmailService {

  void sendHtmlEmail(String to, String subject, String htmlBody);
}
