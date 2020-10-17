package com.itlei.demorabbitmq.adapter;

import it.lei.boot.data.domain.User;
import it.lei.boot.mongodb.Test;

import java.util.Map;

public class MessageDelegate {
    public void  handleMessage(Byte[] content){
        System.out.println(content.toString());
    }
    public void queue1Handler(String message){
        System.out.println("queue1:"+message);

    }
    public void queue1Handler2(String message){
        System.out.println("queue2:"+message);
    }
    public void jsonHandler(Map map){
        System.out.println(map);
    }
    public void objHandler(User user){
        System.out.println(user);
    }
    public void objHandler(Test test){
        System.out.println(test);
    }
}
