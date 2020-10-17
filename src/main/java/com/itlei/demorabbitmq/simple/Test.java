package com.itlei.demorabbitmq.simple;

import it.lei.boot.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends BaseTest {
    @Autowired
    Producter producter;
    @org.junit.Test
    public  void  simpleTest(){
        producter.send();
    }
}
