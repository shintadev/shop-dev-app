package com.shintadev.shop_dev_app.service.other.impl;

import org.springframework.stereotype.Component;

import com.shintadev.shop_dev_app.service.other.KafkaService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaServiceImpl implements KafkaService {

  // @Autowired
  // private KafkaTemplate<String, String> kafkaTemplate;

  // @Override
  // public void sendMessage(String topic, String message) {
  // CompletableFuture<SendResult<String, String>> future =
  // kafkaTemplate.send(topic, message);

  // future.whenComplete((result, ex) -> {
  // if (ex == null) {
  // log.info("Sent message=[" + message +
  // "] with offset=[" + result.getRecordMetadata().offset() + "]");
  // } else {
  // log.info("Unable to send message=[{}] due to : {}",message, ex.getMessage());
  // }
  // });
  // }

  // @Override
  // @KafkaListener(topics = "test", groupId = "group_test")
  // public void listenMessage(String message) {
  // log.info("Received Kafka message: " + message);
  // }

}
