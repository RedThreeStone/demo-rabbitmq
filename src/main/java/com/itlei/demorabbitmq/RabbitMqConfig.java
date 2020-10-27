package com.itlei.demorabbitmq;

import com.itlei.demorabbitmq.adapter.MessageDelegate;
import com.itlei.demorabbitmq.adapter.convert.ImageMessageConverter;
import com.itlei.demorabbitmq.adapter.convert.PDFMessageConverter;
import com.itlei.demorabbitmq.adapter.convert.TextMessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Autowired
    ConnectionFactory connectionFactory;
    @Qualifier(value ="fairQueue" )
    @Autowired
    Queue fairQueue;

    @Bean(name = "springSimpleQueue")
    public Queue springSimpleQueue(){
        return  new Queue("springsimple");
    }
    @Bean(name = "roundRobinQueue")
    public Queue roundRobinQueue(){
        return new Queue("roundRobinQueue");
    }
    //公平模式(能者多劳)
    @Bean(name = "fairQueue")
    public Queue fairQueue(){
        return new Queue("fairQueue");
    }

    public SimpleMessageListenerContainer messageListenerContainer1(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(fairQueue);
        container.setExposeListenerChannel(true);
        //重回队列
        //container.setDefaultRequeueRejected(false);
        //消费者标签的生成模式
//        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
//            @Override
//            public String createConsumerTag(String queueName) {
//                return queueName+"_" +UUID.randomUUID().toString();
//            }
//        });
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
      //  container.setPrefetchCount(1);
        //设置消息代理
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageDelegate());

        //messageListenerAdapter.setQueueOrTagToMethodName();
        //设置消息转换器
//        map.put("fairQueue","queue1Handler");
//        messageListenerAdapter.setMessageConverter(new MyMessageConverter());
//
        //设置json格式的转换器
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
//        messageListenerAdapter.setDefaultListenerMethod("jsonHandler");
//        container.setMessageListener(messageListenerAdapter);

        //设置实体类转换器
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper jackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        jackson2JavaTypeMapper.setTrustedPackages("*");
//        jackson2JsonMessageConverter.setJavaTypeMapper(jackson2JavaTypeMapper);
//        messageListenerAdapter.setDefaultListenerMethod("objHandler");
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
//        container.setMessageListener(messageListenerAdapter);

        //设置多类映射转换
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
//        javaTypeMapper.setTrustedPackages("*");
//        Map<String, Class<?>> classMap = new HashMap<>();
//        classMap.put("user",User.class);
//        classMap.put("test", Test.class);
//        javaTypeMapper.setIdClassMapping(classMap);
//        jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
//        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
//        messageListenerAdapter.setDefaultListenerMethod("objHandler");
//        container.setMessageListener(messageListenerAdapter);



        messageListenerAdapter.setDefaultListenerMethod("consumeMessage");

        //全局的转换器:
        ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

        TextMessageConverter textConvert = new TextMessageConverter();
        convert.addDelegate("text", textConvert);
        convert.addDelegate("html/text", textConvert);
        convert.addDelegate("xml/text", textConvert);
        convert.addDelegate("text/plain", textConvert);

        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        convert.addDelegate("json", jsonConvert);
        convert.addDelegate("application/json", jsonConvert);

        ImageMessageConverter imageConverter = new ImageMessageConverter();
        convert.addDelegate("image/png", imageConverter);
        convert.addDelegate("image", imageConverter);

        PDFMessageConverter pdfConverter = new PDFMessageConverter();
        convert.addDelegate("application/pdf", pdfConverter);


        messageListenerAdapter.setMessageConverter(convert);
        container.setMessageListener(messageListenerAdapter);

      /*  container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            System.out.println("消 费者1号收到的消息"+new String(body,"utf-8"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        });*/

        return container;
    }
//    @Bean(name = "fairMessageListener2")
//    public SimpleMessageListenerContainer messageListenerContainer2(){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(fairQueue);
//        container.setExposeListenerChannel(true);
//        container.setConcurrentConsumers(2);
//        container.setMaxConcurrentConsumers(5);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setPrefetchCount(2);
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            byte[] body = message.getBody();
//            System.out.println("消费者2号收到的消息"+new String(body,"utf-8"));
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        });
//
//        return container;
//    }

    /*发布订阅模式*/
    @Bean(name = "subQueue1")
    public Queue subQueue1(){
        return new Queue("fanout.A");
    }
    @Bean(name = "subQueue2")
    public Queue subQueue2(){
        return new Queue("fanout.B");
    }
    @Bean(name = "subQueue3")
    public Queue subQueue3(){
        return new Queue("fanout.C");
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return  new FanoutExchange("fanoutExchange");
    }
    @Bean
    Binding bindingExchangeA(Queue subQueue1,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(subQueue1).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue subQueue2,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(subQueue2).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(Queue subQueue3,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(subQueue3).to(fanoutExchange);
    }
    /*路由模式 一个队列可以绑定多个路由Key*/
    @Bean
    public Queue routQueue1(){
        return new Queue("router.A");
    }
    @Bean
    public Queue routQueue2(){
        return new Queue("router.B");
    }
    @Bean
    public  DirectExchange directExchange(){
        return new  DirectExchange("directExchange");
    }
    @Bean
    public Binding bindingRouter1(Queue routQueue1, DirectExchange directExchange){
        return  BindingBuilder.bind(routQueue1).to(directExchange).with("routA");
    }
    @Bean
    public Binding bindingRouter2(Queue routQueue2, DirectExchange directExchange){
        return  BindingBuilder.bind(routQueue2).to(directExchange).with("routB");
    }
    @Bean
    public Binding bindingRouter3(Queue routQueue1, DirectExchange directExchange){
        return  BindingBuilder.bind(routQueue1).to(directExchange).with("routC");
    }
}
