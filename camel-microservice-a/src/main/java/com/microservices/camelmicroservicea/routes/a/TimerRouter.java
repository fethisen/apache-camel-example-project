package com.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class TimerRouter extends RouteBuilder {
    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
//            .bean("getCurrentTimeBean")
//            .bean(getCurrentTimeBean)
// when you have just one method than there's no problem. You don't really need to specify the name of the method
            .bean(getCurrentTimeBean,"getCurrentTime")
            .bean(simpleLoggingProcessingComponent)
            .process(new SimpleLoggingProcessor())
            .to("log:first-timer");
    }
}
@Component
class GetCurrentTimeBean {
    public String getCurrentTime(){
        return "Time now is" + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message){

        logger.info("SimpleLoggingProcessingComponent {}",message);
    }
}

class SimpleLoggingProcessor implements Processor{
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}
