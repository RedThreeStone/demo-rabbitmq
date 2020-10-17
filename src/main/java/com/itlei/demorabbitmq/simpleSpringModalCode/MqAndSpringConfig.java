package com.itlei.demorabbitmq.simpleSpringModalCode;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class MqAndSpringConfig {

    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/for_study");
        return  connectionFactory;
    }

    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        //一定要设置为true 这样spring才能开机自启这个类
        rabbitAdmin.setAutoStartup(true);

        //接下来和boot中的配置一样了
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("queue")).to(new FanoutExchange("name")));
        return  rabbitAdmin;
    }
}
