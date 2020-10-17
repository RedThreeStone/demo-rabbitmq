package com.itlei.demorabbitmq.returnandcomfirm;

import com.rabbitmq.client.Channel;
import it.lei.boot.data.domain.User;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component

public class StanderConsumer {


//    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(value = "springBootTestExChange",durable = "true",autoDelete = "false",type = "topic",internal = "false"),
//            value = @Queue(value = "springBootQueue",durable = "true",exclusive = "false",autoDelete = "false",ignoreDeclarationExceptions = "true"),
//            key = "springBoot.#"))
//    @RabbitHandler
//    public void  onMessage(Message message, Channel channel) throws IOException {
//        Object payload = message.getPayload();
//        System.out.println(payload);
//        Long tag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        channel.basicAck(tag,false);
//    }


    /**
     * spring.rabbitmq.config.user.exchange.name=springBootTestExChange
     * spring.rabbitmq.config.user.exchange.durable=true
     * spring.rabbitmq.config.user.exchange.autoDelete=false
     * spring.rabbitmq.config.user.exchange.type=topic
     * spring.rabbitmq.config.user.exchange.internal=false
     * spring.rabbitmq.config.user.queue.name=springBootQueue
     * spring.rabbitmq.config.user.queue.durable=true
     * spring.rabbitmq.config.user.queue.exclusive=false
     * spring.rabbitmq.config.user.queue.autoDelete=false
     * spring.rabbitmq.config.user.queue.ignoreDeclarationExceptions =true
     *
     */
    @RabbitListener(bindings = @QueueBinding(exchange = @Exchange(value = "${spring.rabbitmq.config.user.exchange.name}",
            durable = "${spring.rabbitmq.config.user.exchange.durable}", autoDelete = "${spring.rabbitmq.config.user.exchange.autoDelete}",
            type = "${spring.rabbitmq.config.user.exchange.type}",internal = "${spring.rabbitmq.config.user.exchange.internal}"),
            value = @Queue(value = "${spring.rabbitmq.config.user.queue.name}",durable = "${spring.rabbitmq.config.user.queue.durable}",
                    exclusive = "${spring.rabbitmq.config.user.queue.exclusive}",autoDelete = "${spring.rabbitmq.config.user.queue.autoDelete}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.config.user.queue.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.config.user.key}"))
    @RabbitHandler
    public void  onUserHandler(@Payload User user, @Headers Map<String,Object> properties, Channel channel) throws IOException {
        System.out.println(user);
        System.out.println(properties);
        Long tag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag,false);
    }
}
