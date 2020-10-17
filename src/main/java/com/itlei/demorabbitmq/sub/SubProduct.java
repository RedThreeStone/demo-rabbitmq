package com.itlei.demorabbitmq.sub;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubProduct {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        for (int i=0;i<30;i++){
            String message="hello,subQueue"+i;
            rabbitTemplate.convertAndSend("fanoutExchange","",message);
        }

    }
}
