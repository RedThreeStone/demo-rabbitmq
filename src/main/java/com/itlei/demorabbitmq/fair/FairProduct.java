package com.itlei.demorabbitmq.fair;

import com.alibaba.fastjson.JSONObject;
import it.lei.boot.data.domain.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FairProduct {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        for (int i=0;i<30;i++){
            String message="hello,fairQueue"+i;
            rabbitTemplate.convertAndSend("fairQueue",message);
        }
    }
    public void  jsonSend(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","1号");
        hashMap.put("age","28");
        String jsonStr=JSONObject.toJSON(hashMap).toString();
        System.out.println(jsonStr);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        Message message = new Message(jsonStr.getBytes(),properties);
        rabbitTemplate.send("fairQueue",message);
    }
    public void  objSend(){
        User user = new User();
        user.setUsername("黄磊");
        user.setUserSex("nan");
        String jsonStr=JSONObject.toJSON(user).toString();
        System.out.println(jsonStr);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.getHeaders().put("__TypeId__","it.lei.boot.data.domain.User");
        Message message = new Message(jsonStr.getBytes(),properties);
        rabbitTemplate.convertAndSend("fairQueue",message);
    }
    public void  mulSend(){
        User user = new User();
        user.setUsername("黄磊2");
        user.setUserSex("nan2");
        String jsonStr=JSONObject.toJSON(user).toString();
        System.out.println(jsonStr);
        MessageProperties properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.getHeaders().put("__TypeId__","user");
        Message message = new Message(jsonStr.getBytes(),properties);
        rabbitTemplate.convertAndSend("fairQueue",message);

        user = new User();
        user.setUsername("黄磊3");
        user.setUserSex("nan3");
        jsonStr=JSONObject.toJSON(user).toString();
        System.out.println(jsonStr);
        properties = new MessageProperties();
        properties.setContentType("application/json");
        properties.getHeaders().put("__TypeId__","test");
        message = new Message(jsonStr.getBytes(),properties);
        rabbitTemplate.convertAndSend("fairQueue",message);
    }
    public void  normalSend(Object msg, Map<String,Object> properties){
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        org.springframework.messaging.Message<Object> message = MessageBuilder.createMessage(msg, messageHeaders);
        rabbitTemplate.convertAndSend("fairQueue",message);
    }
}
