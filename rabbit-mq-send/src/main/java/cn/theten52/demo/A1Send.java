package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 官网教程第一篇：HelloWorld。
 * <p>
 * https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * <p>
 * https://www.hxstrive.com/subject/rabbitmq/1051.htm
 *
 * @author wangjin
 * @date 2021/12/18
 */
@Slf4j
public class A1Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        //创建一个连接 Connection 和通道 Channel
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            //声明队列，名称为 QUEUE_NAME。声明一个队列是幂等的 —— 只有当它不存在时才会创建它。
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 10000; i++) {
                String message = "Hello World!";
                //给名称为 QUEUE_NAME 的队列发送消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                log.info(" [x] Sent '" + message + "'");
            }
        }
    }
}
