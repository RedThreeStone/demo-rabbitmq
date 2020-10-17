package com.itlei.demorabbitmq.fair;

import it.lei.boot.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class Test extends BaseTest {
    @Autowired
    FairProduct fairProduct;
    @org.junit.Test
    public  void  simpleTest(){
        //fairProduct.send();
       // fairProduct.jsonSend();
       // fairProduct.objSend();
        fairProduct.mulSend();
        while (true){

        }
    }
}
