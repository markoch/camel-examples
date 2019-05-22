package com.mkoch.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


/*
 * Camel route using a Time Start component:
 * 
 * from("timer://myTimer?period=2000")
 * .setBody()
 * .simple("Hello World Camel fired at ${header.firedTime}")
 * .to("stream:out") 
 */
public class CamelTimerExample {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("timer://myTimer?period=2000")
                    .setBody()
                    .simple("Hello World Camel fired at ${header.firedTime}")
                    .to("stream:out");
                }
            });
            context.start();
            Thread.sleep(10000);
        } finally {
            context.stop();
        }
    }
}