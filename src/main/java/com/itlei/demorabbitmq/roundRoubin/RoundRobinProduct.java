package com.itlei.demorabbitmq.roundRoubin;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoundRobinProduct{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        for (int i=0;i<30;i++){
            String message="hello,我是RoundRoubin"+i;
            rabbitTemplate.convertAndSend("roundRobinQueue",message);
        }

    }
}
