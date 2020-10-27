package com.itlei.demorabbitmq.bootapi;

import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.simple.Producter;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleTest extends DemoRabbitmqApplicationTests {
    @Autowired
    Producter producter;

    @org.junit.jupiter.api.Test
    public  void  simpleTest(){
        producter.send();
    }
}
