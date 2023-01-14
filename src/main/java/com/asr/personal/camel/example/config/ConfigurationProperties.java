package com.asr.personal.camel.example.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConfigurationProperties {

  String redisPassword;

  String redisHost;

  Integer redisPort;

  String applicationName;

  public ConfigurationProperties(
      @Value("${redis.password}") String redisPassword,
      @Value("${redis.host:127.0.0.1}") String redisHost,
      @Value("${redis.port:6379}") Integer redisPort,
      @Value("${application.name}") String applicationName) {
    this.redisPassword = redisPassword;
    this.redisHost = redisHost;
    this.redisPort = redisPort;
    this.applicationName = applicationName;
  }
}
