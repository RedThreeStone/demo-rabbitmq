package com.itlei.demorabbitmq.bootapi;

import com.itlei.demorabbitmq.DemoRabbitmqApplicationTests;
import com.itlei.demorabbitmq.fair.FairProduct;
import org.springframework.beans.factory.annotation.Autowired;

public class FairTest extends DemoRabbitmqApplicationTests {
    @Autowired
    FairProduct fairProduct;

    public  void  simpleTest(){
        //fairProduct.send();
       // fairProduct.jsonSend();
       // fairProduct.objSend();
        fairProduct.mulSend();
        while (true){

        }
    }
}
