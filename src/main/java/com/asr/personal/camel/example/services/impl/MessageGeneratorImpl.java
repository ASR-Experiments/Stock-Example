package com.asr.personal.camel.example.services.impl;

import com.asr.personal.camel.example.services.MessageGenerator;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MessageGeneratorImpl implements MessageGenerator {

  @Override
  public Map<String, Object> getMessage() {
    String value = "My Message: " + UUID.randomUUID() + " at " + LocalDateTime.now();
    return Map.of("value", value, "ttl", 10000L, "key", "key");
  }
}
