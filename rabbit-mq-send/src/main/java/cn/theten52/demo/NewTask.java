package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author wangjin
 */
@Slf4j
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

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
