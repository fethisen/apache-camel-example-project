package com.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        //timer miliseconds
//        from("timer:active-mq-timer?period=10000")
//            .transform().constant("My message for Active MQ")
//            .log("${body}")
//            .to("activemq:my-activemq-queue"); //endpoint
//        //queue

//        from("file:files/json")
//            .log("${body}")
//            .to("activemq:my-activemq-queue");

//        from("file:files/xml")
//            .log("${body}")
//            .to("activemq:my-activemq-xml-queue");
        from("file:files/input")
            .unmarshal().csv()
            .split(body().tokenize(","))
            .log("Read line ${body}")
            .choice()
                .when(body().contains("dvd"))
                    .to("activemq:DVD_Orders")
                .when(body().contains("cd"))
                    .to("activemq:CD_Orders")
                .otherwise()
                    .log("A different order")
            .end();
    }
}
