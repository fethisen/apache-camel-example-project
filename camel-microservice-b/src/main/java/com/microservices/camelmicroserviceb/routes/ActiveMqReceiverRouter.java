package com.microservices.camelmicroserviceb.routes;

import com.microservices.camelmicroserviceb.CurrencyExchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class ActiveMqReceiverRouter extends RouteBuilder {
    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
    @Autowired
    private MyCurrencyExchangeTramsformer myCurrencyExchangeTramsformer;

    @Override
    public void configure() throws Exception {

        from("activemq:my-activemq-json-queue") //endpoint
            .unmarshal()
            .json(JsonLibrary.Jackson, CurrencyExchange.class)
            .log(body().toString())
            .bean(myCurrencyExchangeProcessor)
            .bean(myCurrencyExchangeTramsformer)
            .to("log:received-message-from-active-mq");

//        from("activemq:my-activemq-xml-queue") //endpoint
//            .unmarshal()
//            .jacksonXml(CurrencyExchange.class)
//            .to("log:received-message-from-active-mq");

//        from("activemq:split-queue")
//            .to("log:received-message-from-active-mq");
    }
}

@Component
class MyCurrencyExchangeProcessor {

    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange){
        logger.info("Do some processing with currencyExchange.getConversionMultiple() value whic is .{}"
            ,currencyExchange.getConversionMultiple());
    }
}

@Component
class MyCurrencyExchangeTramsformer{
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);
    public CurrencyExchange processMessage(CurrencyExchange currencyExchange){
        currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
        return currencyExchange;
    }
}
