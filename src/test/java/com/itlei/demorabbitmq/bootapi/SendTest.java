package com.itlei.demorabbitmq.bootapi;


import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.User;
import com.itlei.demorabbitmq.returnandcomfirm.StanderPublish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SendTest extends DemoRabbitmqApplicationTests {
    @Autowired
    private StanderPublish standerPublish;
    @Test
    public void  callBackTest(){
       // standerPublish.sendMessage("6666",null);
        User user = new User();
        user.setUserSex("男");
        user.setUsername("小明");

        standerPublish.sendMessage(user,null);
        while (true){

        }
    }
}
