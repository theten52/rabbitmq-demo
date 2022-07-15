package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

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
public class A1Recv {
    // 队列名称
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        //为什么我们不使用 try-with-resource 语句来自动关闭通道和连接呢？
        // 因为这样我们的程序运行后会直接接关闭Connection并退出。
        // 这会很尴尬，因为我们希望进程在消费者异步侦听消息到达时保持活动状态。
        Connection connection = factory.newConnection();
        log.info("connection rabbit mq success!");

        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列-请注意，我们也在这里声明了队列。因为我们可能在发布者之前启动消费者，所以我们要确保队列存在，然后再尝试从中消费消息。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        log.info(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received '" + message + "'");
        };

        //注册消费者逻辑到队列 QUEUE_NAME 上。
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
