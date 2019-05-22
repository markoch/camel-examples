package com.mkoch.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/*
 * Camel route:
 * 
 * from("activemq:queue:TRADE.Order.Q"")
 * .to("stream:out")
 */
//Add logging step via: .wireTap("log:?level=INFO&showBody=true") between from and to
public class CamelDSLExample {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        try {
            context.addComponent("jms", ActiveMQComponent.activeMQComponent("tcp://127.0.0.1:8161"));
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("activemq:queue:TRADE.Order.Q")
                    .to("stream:out");
                }
            });
            ProducerTemplate template = context.createProducerTemplate();
            context.start();
            template.sendBody("activemq:TRADE.Order.Q", "Hello World");
            Thread.sleep(2000);
        } finally {
            context.stop();
        }
    }
}
