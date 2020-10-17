package com.itlei.demorabbitmq.returnandcomfirm;

import it.lei.boot.BaseTest;
import it.lei.boot.data.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SendTest extends BaseTest {
    @Autowired
    private  StanderPublish standerPublish;
    @Test
    public void  callBackTest(){
       // standerPublish.sendMessage("6666",null);
        User user = new User();
        user.setUserSex("男");
        user.setUsername("小明");
        user.setSsmUserId(100);
        standerPublish.sendMessage(user,null);
        while (true){

        }
    }
}
