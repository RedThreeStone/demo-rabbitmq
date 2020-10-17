package com.itlei.demorabbitmq.sub;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.A")
public class SubConsumer1 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer1:"+message);
    }
}
