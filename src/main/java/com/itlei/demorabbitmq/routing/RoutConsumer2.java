package com.itlei.demorabbitmq.routing;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "router.B")
public class RoutConsumer2 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer2:"+message);
    }
}
