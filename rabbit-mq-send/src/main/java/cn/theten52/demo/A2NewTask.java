package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 官网教程第二篇：Worker。
 * <p>
 * https://www.rabbitmq.com/tutorials/tutorial-two-java.html
 * <p>
 * https://www.hxstrive.com/subject/rabbitmq/1051.htm
 * <p>
 * https://www.cnblogs.com/ZhuChangwu/p/14093107.html#rabbitmq%E7%9A%84%E4%BA%94%E7%A7%8D%E6%B6%88%E6%81%AF%E6%A8%A1%E5%9E%8B
 *
 * @author wangjin
 */
@Slf4j
public class A2NewTask {

    private static final String TASK_QUEUE_NAME = "tut2.task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.queueDeclare(TASK_QUEUE_NAME,
                /*持久化打开*/true,
                false, false, null);

            for (int i = 0; i < 6; i++) {

                String message = String.join(" ", "hello...");

                channel.basicPublish("", TASK_QUEUE_NAME,
                    /*文本方式持久化消息*/ MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8));

                log.info(" [x] Sent '" + message + "'");
            }
        }
    }

}
