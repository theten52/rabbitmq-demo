package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 官网教程第三篇：Publish/Subscribe。
 * <p>
 * https://www.rabbitmq.com/tutorials/tutorial-three-java.html
 * <p>
 * https://www.hxstrive.com/subject/rabbitmq/1051.htm
 * <p>
 * https://www.cnblogs.com/ZhuChangwu/p/14093107.html#rabbitmq%E7%9A%84%E4%BA%94%E7%A7%8D%E6%B6%88%E6%81%AF%E6%A8%A1%E5%9E%8B
 *
 * @author wangjin
 */
@Slf4j
public class A3EmitLog {

    private static final String EXCHANGE_NAME = "tut3.logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = argv.length < 1 ? "info: Hello World!" :
                String.join(" ", argv);

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            log.info(" [x] Sent '" + message + "'");
        }
    }

}