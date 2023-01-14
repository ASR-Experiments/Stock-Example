package com.asr.personal.camel.example.routes;

import com.asr.personal.camel.example.constants.RouteConstant;
import com.asr.personal.camel.example.services.MessageGenerator;
import org.springframework.stereotype.Component;

@Component
public class BasicRoute extends org.apache.camel.builder.RouteBuilder {

  @Override
  public void configure() {
    from(RouteConstant.FIRST_ROUTE)
        .bean(MessageGenerator.class)
        .routeId(RouteConstant.FIRST_ROUTE)
        .tracing()
        .to(RouteConstant.FIRST_DIRECT);

    from(RouteConstant.FIRST_DIRECT)
        .routeId(RouteConstant.FIRST_DIRECT)
        .log("Manual Camel Log : ${body}")
        .log("Message History : ${messageHistory}")
        .to("log:camels-to-logger");
  }
}
