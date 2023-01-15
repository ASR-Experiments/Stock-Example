package com.asr.personal.camel.example.routes;

import com.asr.personal.camel.example.constants.RouteConstant;
import com.asr.personal.camel.example.services.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

//@Component
public class BasicRoute extends org.apache.camel.builder.RouteBuilder {

  @Autowired
  MessageGenerator messageGenerator;

  @Override
  public void configure() {

    from(RouteConstant.FIRST_ROUTE + "?period=10000")
        .bean(messageGenerator)
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
