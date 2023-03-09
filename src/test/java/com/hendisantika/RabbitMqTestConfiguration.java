package com.hendisantika;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 06:04
 * To change this template use File | Settings | File Templates.
 */
@TestConfiguration
public class RabbitMqTestConfiguration {

    private static final String NOTIFICATION_ROUTING_KEY = "notifications";
    private static final String TESTCONTAINERS_TEST_EXCHANGE = "testcontainers.test.exchange";
    private static final String TESTCONTAINERS_TEST_QUEUE = "testcontainers.test.queue";

    @Bean
    Queue queue() {
        return new Queue(TESTCONTAINERS_TEST_QUEUE, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(TESTCONTAINERS_TEST_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(NOTIFICATION_ROUTING_KEY);
    }
}
