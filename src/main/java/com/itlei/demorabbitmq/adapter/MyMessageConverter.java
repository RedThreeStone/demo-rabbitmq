package com.itlei.demorabbitmq.adapter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class MyMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(o.toString().getBytes(),messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] body = message.getBody();
        String contentType = message.getMessageProperties().getContentType();
        if(contentType.contains("text")){
            return  body.toString();
        }else {
            System.out.println( message.getBody().toString());
            return message;
        }

    }
}
