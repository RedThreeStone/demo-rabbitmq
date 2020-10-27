package com.itlei.demorabbitmq.bootapi;

import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.routing.RouterProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoutingKeyTest extends DemoRabbitmqApplicationTests {
    @Autowired
    RouterProduct routerProduct;
    @Test
    public  void  simpleTest(){
        routerProduct.send();
        while (true){

        }
    }
}
