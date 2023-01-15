package com.asr.personal.camel.example.controller;

import com.asr.personal.camel.example.constants.RouteConstant;
import com.asr.personal.camel.example.routes.proxy.RedisRouteProxy;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Produce;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  RedisRouteProxy redisRouteProxyPut;

  @Produce(RouteConstant.REDIS_STRING_GET)
  RedisRouteProxy redisRouteProxyGet;

  @PostMapping
  public void putObject(
      @RequestBody Object value,
      @RequestHeader(value = "key", required = false) String key) {

    Map<String, Object> request = new HashMap<>();
    request.put("value", value);
    if (StringUtils.hasText(key)) { request.put("key", key); }
    redisRouteProxyPut.start(request);
  }

  @GetMapping(value = "/{key}", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Object> getObject(@PathVariable String key) {
    Object response = redisRouteProxyGet.start(Map.of("key", key));
    if (ObjectUtils.isEmpty(response)) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(response);
  }
}
