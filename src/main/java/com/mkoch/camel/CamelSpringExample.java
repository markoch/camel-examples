package com.mkoch.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Camel route using Spring framework:
 * 
 * from("activemq:TRADE.Order.Q")
 *.to("bean:myBean?method=appendCamel")
 *.to("stream:out") 
 */
public class CamelSpringExample {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
        try {
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            template.sendBody("activemq:TRADE.Order.Q", "Hello World");
        } finally {
            camelContext.stop();
        }
    }
}