package com.shintadev.shop_dev_app.service;

import java.util.Map;

public interface KafkaService {

  void sendMessage(String topic, String message);

  void listenMessage(String message);
}
