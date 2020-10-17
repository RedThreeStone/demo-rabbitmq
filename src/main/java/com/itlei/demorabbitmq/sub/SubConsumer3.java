package com.itlei.demorabbitmq.sub;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.C")
public class SubConsumer3 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer3:"+message);
    }
}
