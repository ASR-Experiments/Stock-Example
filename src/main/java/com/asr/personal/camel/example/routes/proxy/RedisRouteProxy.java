package com.asr.personal.camel.example.routes.proxy;

import java.util.Map;

public interface RedisRouteProxy {

  String start(Map<String, Object> details);
}
