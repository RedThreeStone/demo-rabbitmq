package com.itlei.demorabbitmq.sub;

import it.lei.boot.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends BaseTest {
    @Autowired
    SubProduct subProduct;
    @org.junit.Test
    public  void  simpleTest(){
        subProduct.send();
        while (true){

        }
    }
}
