package com.asr.personal.camel.example.routes;

import com.asr.personal.camel.example.config.ConfigurationProperties;
import com.asr.personal.camel.example.config.RedisConfiguration;
import com.asr.personal.camel.example.constants.RouteConstant;
import com.asr.personal.camel.example.services.MessageGenerator;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.redis.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCommand;
import org.springframework.stereotype.Component;

@Component
public class RedisRoute extends RouteBuilder {

  @Autowired
  ConfigurationProperties configurationProperties;

  @Autowired
  MessageGenerator messageGenerator;

  @Override
  public void configure() throws Exception {

    // @formatter:off
    from(RouteConstant.FIRST_ROUTE + "?period=10000")
        .bean(messageGenerator)
        .routeId(RouteConstant.FIRST_ROUTE)
        .tracing()
        .to(RouteConstant.REDIS_STRING_PUT);
    // @formatter:on

    // @formatter:off
    from(RouteConstant.REDIS_STRING_PUT)
        .routeId(RouteConstant.REDIS_STRING_PUT)
        .setHeader(RedisConstants.COMMAND, RedisCommand.SET::name)
          .id("set-command")
        .setHeader(RedisConstants.VALUE, simple("${body[value]}"))
          .id("set-value")
        .log("Message History : ${messageHistory}")
          .id("show-history")
        .choice()
          .when(simple("${body[key]}").isNull())
            .id("set-default-key")
            .setHeader(RedisConstants.KEY, simple("Random ${random(100)}", String.class))
          .otherwise()
            .id("set-key")
            .setHeader(RedisConstants.KEY, simple("${body[key]}", String.class))
        .end()
        .choice()
          .when(simple("${body[ttl]}").isNotNull())
            .setHeader(RedisConstants.TIMEOUT, simple("${body[ttl]}", Long.class))
            .id("set-ttl")
        .end()
        .to(String.format("spring-redis:%s:%d?redisTemplate=#%s",
                          configurationProperties.getRedisHost(),
                          configurationProperties.getRedisPort(),
                          RedisConfiguration.REDIS_TEMPLATE));
    // @formatter:on
  }
}
