package com.itlei.demorabbitmq.adapter;

import com.itlei.demorabbitmq.User;


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

}
