package com.itlei.demorabbitmq.routing;

import it.lei.boot.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends BaseTest {
    @Autowired
    RouterProduct routerProduct;
    @org.junit.Test
    public  void  simpleTest(){
        routerProduct.send();
        while (true){

        }
    }
}
