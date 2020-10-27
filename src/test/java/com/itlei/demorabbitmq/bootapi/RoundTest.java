package com.itlei.demorabbitmq.bootapi;

import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.roundRoubin.RoundRobinProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoundTest extends DemoRabbitmqApplicationTests {
    @Autowired
    RoundRobinProduct roundRoubinProducter;
    @Test
    public  void  simpleTest(){
        roundRoubinProducter.send();

    }
}
