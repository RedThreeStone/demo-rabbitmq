package com.itlei.demorabbitmq.bootapi;

import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.sub.SubProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FanoutTest extends DemoRabbitmqApplicationTests {
    @Autowired
    SubProduct subProduct;
    @Test
    public  void  simpleTest(){
        subProduct.send();
        while (true){

        }
    }
}
