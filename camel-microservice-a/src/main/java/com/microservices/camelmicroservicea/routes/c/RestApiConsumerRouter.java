package com.microservices.camelmicroservicea.routes.c;

import io.micrometer.core.instrument.util.StringUtils;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


//@Component
public class RestApiConsumerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(8000);
        from("timer:rest-api-consumer?period=10000")
            .log("${body}")
            .setHeader("from", () -> "EUR")
            .setHeader("to", () -> "INR")
            .to("rest:get:/currency-exchange/from/{from}/to/{to}")
            .log("${body}");
    }
}
