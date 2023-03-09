package com.hendisantika.inbound;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.NotificationListener;
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
    public void receiveMessage(String message) throws JsonParseException, IOException {

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
