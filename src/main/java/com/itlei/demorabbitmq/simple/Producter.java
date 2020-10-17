package com.itlei.demorabbitmq.simple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producter {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String message="hello,我是简单模式";
        rabbitTemplate.convertAndSend("springsimple",message);
    }
}
