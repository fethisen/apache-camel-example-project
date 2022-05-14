package com.microservices.camelmicroservicea.routes.b;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyFileRouter extends RouteBuilder {

    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        from("file:files/input")
            .routeId("Files-Input-Route")
            .transform().body(String.class)
            .choice()// Content base routing
                .when(simple("${file:ext} ends with 'xml'"))
                    .log("XML File")
                    .to("activemq:my-activemq-xml-queue")
                .when(simple("${file:ext} ends with 'json'"))
                    .log("Json File")
                    .to("activemq:my-activemq-json-queue")
                .when(simple("${body} contains 'USD'"))
                    .log("message contains USD ${body}")
                .when(method(deciderBean))
                .otherwise()
                    .log("Different option")
            .end()
            .to("direct:log-file-values")
            .to("file:files/output2");

//        from("direct:log-file-values")
//            //            .log("${body}")
//            .log("log-file-values message history:  ${messageHistory} ${file:absolute.path}")
//            .log("log-file-values file name:  ${file:name} ${file:name.ext} ${file:onlyname}")
//            .log("log-file-values file only name:  ${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
//            .log("log-file-values file size:  ${file:size} ${file:modified}")
//            .log("log-file-values file route id:  ${routeId} ${camelId} ${body}");
    }
}

@Component
class DeciderBean {
    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(@Body  String body, @Headers Map<String, String> headers) {
        logger.info("DeciderBean {} {}",body, headers);
        return true;
    }
}
