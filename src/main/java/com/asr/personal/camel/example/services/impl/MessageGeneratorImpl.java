package com.asr.personal.camel.example.services.impl;

import com.asr.personal.camel.example.services.MessageGenerator;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MessageGeneratorImpl implements MessageGenerator {

  @Override
  public String getMessage() {
    return "My Message: " + UUID.randomUUID() + " at " + LocalDateTime.now().toString();
  }

}
