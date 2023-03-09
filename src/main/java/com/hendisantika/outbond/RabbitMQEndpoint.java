package com.hendisantika.outbond;

import com.hendisantika.routing.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:54
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RabbitMQEndpoint implements Endpoint {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEndpoint(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Notification notification) {

        rabbitTemplate.convertAndSend(notification);

    }
}
