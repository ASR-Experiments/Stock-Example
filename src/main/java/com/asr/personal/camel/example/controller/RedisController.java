package com.asr.personal.camel.example.controller;

import com.asr.personal.camel.example.constants.RouteConstant;
import com.asr.personal.camel.example.routes.proxy.RedisRouteProxy;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Produce;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

  @Produce(RouteConstant.REDIS_STRING_PUT)
  RedisRouteProxy redisRouteProxy;

  @PostMapping
  public void putObject(
      @RequestBody Object value,
      @RequestHeader(value = "key", required = false) String key) {

    Map<String, Object> request = new HashMap<>();
    request.put("value", value);
    if (StringUtils.hasText(key)) { request.put("key", key); }
    redisRouteProxy.start(request);
  }
}
