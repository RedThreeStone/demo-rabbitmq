package com.itlei.demorabbitmq.baseapi;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author uu
 * @date 2020/7/25 19:44
 * @desciption 死信队列(延迟队列) 当业务queue中的消息 被nack或reject之后并设置重回队列为false,队列达到最大长度,消息ttl到期.进入死信队列
 *          args.put("x-dead-letter-exchange",DeadEXECHANGENAME);
 *             args.put("x-dead-letter-routing-key",DEADROUTINGKEY);
 */
public class Deadline {
    private final String HOST="129.204.172.72";
    private final int PORT=5672;
    private final String USERNAME="guest";
    private final String PASSWORD="guest";
    private final String VIRTUALHOST="/review";
    private final String QUEUENAME="dealingQue";
    private final String DeadQUEUENAME="deadQue";
    private final String EXECHANGENAME="dealingchange";
    private final String DeadEXECHANGENAME="deadExechange";
    private final String ROUTINGKEY="QC";
    private final String DEADROUTINGKEY="QCDEAD";

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
            channel.exchangeDeclare(EXECHANGENAME, BuiltinExchangeType.DIRECT,true,false,false,null);
            //如果不指定交换机 有个默认的交换机会被使用 routingkey 名为queue的名字,且不需要显示绑定
            HashMap<String, Object> args = new HashMap<>();
            //指定死信交换机
            args.put("x-dead-letter-exchange",DeadEXECHANGENAME);
            //如果不指定 就按原来的key发送
            args.put("x-dead-letter-routing-key",DEADROUTINGKEY);
            args.put("x-message-ttl",10*1000);
            channel.queueDeclare(QUEUENAME,true,false,false,args);
            channel.queueBind(QUEUENAME,EXECHANGENAME,ROUTINGKEY);
            //创建死信队列和死信交换机并绑定
            channel.exchangeDeclare(DeadEXECHANGENAME, BuiltinExchangeType.DIRECT,true,false,false,null);
            channel.queueDeclare(DeadQUEUENAME,true,false,false,null);
            channel.queueBind(DeadQUEUENAME,DeadEXECHANGENAME,DEADROUTINGKEY);
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
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        //设置消息的过期时间
        builder.expiration("5000");
        channel.basicPublish(EXECHANGENAME,ROUTINGKEY,false,false,null,"helloDEadLetter".getBytes("UTF-8"));
    }
    @Test
    public void consumer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(DeadQUEUENAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("routingkey:"+envelope.getRoutingKey());
                System.out.println("消息:"+new String(body,"UTF-8"));
            }
        });
        while (true){

        }
    }
}
