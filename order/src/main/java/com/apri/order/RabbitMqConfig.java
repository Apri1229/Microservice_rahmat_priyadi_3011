package com.apri.order;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_NAME = "order.queue";

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}