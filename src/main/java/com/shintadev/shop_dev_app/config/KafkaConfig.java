package com.shintadev.shop_dev_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {

  /* Kafka producer configuration */
  // @Bean
  // public ProducerFactory<String, String> producerFactory() {
  // Map<String, Object> configProps = new HashMap<>();
  // configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
  // StringSerializer.class);
  // configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
  // JsonSerializer.class);
    
  // return new DefaultKafkaProducerFactory<>(configProps);
  // }

  // @Bean
  // public KafkaTemplate<String, String> kafkaTemplate() {
  // return new KafkaTemplate<>(producerFactory());
  // }

  // /* Kafka consumer configuration */
  // @Bean
  // public ConsumerFactory<String, String> consumerFactory() {
  // Map<String, Object> configProps = new HashMap<>();
  // configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
  // StringDeserializer.class);
  // configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
  // JsonDeserializer.class);
      
  // return new DefaultKafkaConsumerFactory<>(configProps);
  // }

  // @Bean
  // public ConcurrentKafkaListenerContainerFactory<String, String>
  // kafkaListenerContainerFactory() {
  // ConcurrentKafkaListenerContainerFactory<String, String> factory = new
  // ConcurrentKafkaListenerContainerFactory<>();
  // factory.setConsumerFactory(consumerFactory());
      
  // return factory;
  // }
}
