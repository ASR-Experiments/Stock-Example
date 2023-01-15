package com.asr.personal.camel.example.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RouteConstant {

  public static final String REDIS_STRING_PUT = "direct:redis-put";

  public static final String FIRST_ROUTE = "timer:first-route";

  public static final String FIRST_DIRECT = "direct:first-route";

  public static final String REDIS_STRING_GET = "direct:redis-get";
}
