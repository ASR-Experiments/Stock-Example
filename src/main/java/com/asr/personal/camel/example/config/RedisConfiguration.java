package com.asr.personal.camel.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfiguration {

  public static final String REDIS_CONNECTION_FACTORY = "customRedisConnectionFactory";

  public static final String REDIS_TEMPLATE = "customRedisTemplate";

  @Autowired
  ConfigurationProperties configurationProperties;

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
        configurationProperties.getRedisHost(),
        configurationProperties.getRedisPort()
    );
    redisStandaloneConfiguration.setPassword(configurationProperties.getRedisPassword());
    return redisStandaloneConfiguration;
  }

  @Bean(RedisConfiguration.REDIS_CONNECTION_FACTORY)
  public RedisConnectionFactory connectionFactory(
      org.springframework.data.redis.connection.RedisConfiguration redisConfiguration) {
    LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration
        .builder()
        .clientName(configurationProperties.getApplicationName())
        .build();
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(
        redisConfiguration, clientConfiguration);
    connectionFactory.afterPropertiesSet();
    return connectionFactory;
  }

  @Bean(RedisConfiguration.REDIS_TEMPLATE)
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setKeySerializer(RedisSerializer.string());
    template.setHashKeySerializer(RedisSerializer.string());
    template.setHashValueSerializer(RedisSerializer.json());
    template.setValueSerializer(RedisSerializer.json());
    template.setConnectionFactory(redisConnectionFactory);
    template.afterPropertiesSet();
    return template;
  }
}
