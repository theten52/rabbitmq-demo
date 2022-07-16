# docker 安装 RabbitMQ
```terminal
docker run -d --hostname my-rabbit --name some-rabbit -p 15671:15671 -p 15672:15672 -p 15691:15691 -p 15692:15692 -p 25672:25672 -p 4369:4369 -p 5671:5671 -p 5672:5672 rabbitmq:3-management
```

# Worker模式介绍
+ 多客户端同时消费一个队列的消息，竞争消费。提高客户端消息消费速度。
+ 补充点1：可以给队列添加一条属性，不再是队列把任务平均分配开给消费者。而是让消费者消费完了后，问队列要新的任务，这样能者多劳。
```java
    // 设置每个消费者同时只能处理一条消息
    channel.basicQos(1);
```
+ 补充点2：接受者接受消息时，可以像下图这样配置消费端手动ACK。
```java
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

        log.info(" [x] Received '" + message + "'");
        try {
            doWork(message);
        } finally {
            log.info(" [x] Done");
            //手动确认消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    };
    channel.basicConsume(TASK_QUEUE_NAME,
        /*手动确认消息 */false,
        deliverCallback,
        consumerTag -> {
        }
    );
```