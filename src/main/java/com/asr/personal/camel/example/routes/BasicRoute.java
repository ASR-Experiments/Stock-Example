package com.asr.personal.camel.example.routes;

import com.asr.personal.camel.example.constants.RouteConstant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BasicRoute extends org.apache.camel.builder.RouteBuilder {

  @Override
  public void configure() {
    from(RouteConstant.FIRST_ROUTE)
        .setBody(constant("My Message = " + UUID.randomUUID()))
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
