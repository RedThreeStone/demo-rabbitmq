package com.itlei.demorabbitmq.routing;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouterProduct {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        for (int i=0;i<30;i++){
            //发送带有额外属性的消息
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            messageProperties.setExpiration("10000");
            messageProperties.setContentEncoding("UTF-8");


            Message message = new Message(("hello,subQueue"+i).getBytes(),messageProperties);
            rabbitTemplate.convertAndSend("directExchange","routC",message);
        }

    }
}
