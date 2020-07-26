package com.itlei.demorabbitmq.baseapi;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author uu
 * @date 2020/7/25 16:34
 * @desciption exechange bind 指的是交换机间的绑定,数据到达交换机A后 会分发给交换机B  这个时候绑定了交换机A的队列也能消费,感觉可以拿在做日志
 */
public class ExechangeBind {
    private final String HOST="129.204.172.72";
    private final int PORT=5672;
    private final String USERNAME="guest";
    private final String PASSWORD="guest";
    private final String VIRTUALHOST="/review";
    private final String QUEUENAME1="bq1";
    private final String QUEUENAME2="bq2";
    private final String EXECHANGENAME1="bindexchange1";
    private final String EXECHANGENAME2="bindexchange2";
    private final String ROUTINGKEY1="QC";

    /**
     * 一个channel相当于一个会话
     * @throws IOException
     * @throws TimeoutException
     */
    public Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setVirtualHost(VIRTUALHOST);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        Connection connection = connectionFactory.newConnection();
        return connection;
    }

    /**
     * queue参数设置的参考博文https://blog.csdn.net/fsgsggd/article/details/81349553
     * 特别的exclusive是指一个队列同时只能有一个消费者消费
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void createQueueAndExechange() throws IOException, TimeoutException {
        Connection connection=null;
        Channel channel=null;
        try {
            connection = getConnection();
            channel = connection.createChannel();
            //将无法路由的信息 发送到备用交换机
//            Map<String, Object> propties = new HashMap<>();
//            propties.put("alternate-exchange","备用交换机名");
            channel.exchangeDeclare(EXECHANGENAME1, BuiltinExchangeType.DIRECT,true,false,false,null);
            channel.exchangeDeclare(EXECHANGENAME2, BuiltinExchangeType.DIRECT,true,false,false,null);
            //如果不指定交换机 有个默认的交换机会被使用 routingkey 名为queue的名字,且不需要显示绑定
            channel.queueDeclare(QUEUENAME1,true,false,false,null);
            channel.queueDeclare(QUEUENAME2,true,false,false,null);
            channel.queueBind(QUEUENAME1,EXECHANGENAME1,ROUTINGKEY1);
            channel.queueBind(QUEUENAME2,EXECHANGENAME2,ROUTINGKEY1);
            //设置交换机间互相绑定
            channel.exchangeBind(EXECHANGENAME2,EXECHANGENAME1,ROUTINGKEY1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel!=null){
                channel.close();
            }
            if (connection!=null){
                connection.close();
            }

        }
    }
    @Test
    public void producer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //交换器无法根据自动的类型和路由键找到一个符合条件的队列，那么RabbitMq会调用Basic.Ruturn命令将消息返回给生产都，为false时，出现上述情况消息被直接丢弃
        //如果交换器在消息路由到队列时发现没有任何消费者，那么
        //这个消息将不会存和队列，当与路由匹配的所有队列都没有消费者时，会Basic.Return返回给生产者3.0去掉了immediate 参数
        //immediate和mandatory 都是消息传递过程中，不可达目的地是，将消息返回给生产者的功能
        channel.basicPublish(EXECHANGENAME1,ROUTINGKEY1,false,false,null,"hello".getBytes("UTF-8"));
    }
    @Test
    public void consumer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(QUEUENAME1,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("exechangename:"+envelope.getExchange());
                System.out.println("routingkey:"+envelope.getRoutingKey());
                System.out.println("消息:"+new String(body,"UTF-8"));
            }
        });
        while (true){

        }
    }
    @Test
    public void consumer2() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(QUEUENAME2,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("exechangename:"+envelope.getExchange());
                System.out.println("routingkey:"+envelope.getRoutingKey());
                System.out.println("消息:"+new String(body,"UTF-8"));
            }
        });
        while (true){

        }
    }
}
