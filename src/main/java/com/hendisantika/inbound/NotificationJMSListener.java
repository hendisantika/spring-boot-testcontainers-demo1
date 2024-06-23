package com.hendisantika.inbound;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hendisantika.routing.Notification;
import com.hendisantika.routing.NotificationListener;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:52
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class NotificationJMSListener {

    private final NotificationListener listener;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "jms.events")
    public void receiveMessage(String message) throws IOException {

        // deserialize
        JMSNotification jmsNotification = objectMapper.readValue(message, JMSNotification.class);

        // translate
        Notification notification = translate(jmsNotification);

        // forward to domain
        listener.onNotification(notification);

    }

    private Notification translate(JMSNotification jmsNotification) {
        return new Notification(jmsNotification.getMessage(), "JMS");
    }
}
