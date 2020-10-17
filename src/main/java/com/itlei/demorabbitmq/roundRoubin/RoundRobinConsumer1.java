package com.itlei.demorabbitmq.roundRoubin;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "roundRobinQueue")
public class RoundRobinConsumer1 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer1:"+message);
    }
}
