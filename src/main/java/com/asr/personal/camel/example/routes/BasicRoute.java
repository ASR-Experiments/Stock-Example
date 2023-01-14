package com.asr.personal.camel.example.routes;

import com.asr.personal.camel.example.constants.RouteConstant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BasicRoute extends org.apache.camel.builder.RouteBuilder {

  @Override
  public void configure() {
    from(RouteConstant.FIRST_ROUTE)
        .routeId(RouteConstant.FIRST_ROUTE)
        .tracing()
        .setBody(constant("My Message = " + UUID.randomUUID()))
        .log("Manual Camel Log : ${body}")
        .to("log:camels-to-logger");
  }
}
