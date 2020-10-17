package com.itlei.demorabbitmq.roundRoubin;

import it.lei.boot.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends BaseTest {
    @Autowired
    RoundRobinProduct roundRoubinProducter;
    @org.junit.Test
    public  void  simpleTest(){
        roundRoubinProducter.send();

    }
}
