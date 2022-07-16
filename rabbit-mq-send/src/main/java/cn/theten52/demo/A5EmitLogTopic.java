package cn.theten52.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 官网教程第五篇：Topics。
 * <p>
 * https://www.rabbitmq.com/tutorials/tutorial-five-java.html
 * <p>
 * https://www.hxstrive.com/subject/rabbitmq/1051.htm
 * <p>
 * https://www.cnblogs.com/ZhuChangwu/p/14093107.html#rabbitmq%E7%9A%84%E4%BA%94%E7%A7%8D%E6%B6%88%E6%81%AF%E6%A8%A1%E5%9E%8B
 *
 * @author wangjin
 */
public class A5EmitLogTopic {

    private static final String EXCHANGE_NAME = "tut5.topic_logs";

    public static void main(String[] argv) throws Exception {
        argv = new String[] {"info"};

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1) {
            return "anonymous.info";
        }
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2) {
            return "Hello World!";
        }
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        if (length < startIndex) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}

