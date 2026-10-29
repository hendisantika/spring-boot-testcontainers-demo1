package com.hendisantika.inbound;

import com.hendisantika.routing.Notification;
import com.hendisantika.routing.NotificationListener;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

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
    private final JsonMapper jsonMapper;

    @JmsListener(destination = "jms.events")
    public void receiveMessage(String message) {

        // deserialize
        JMSNotification jmsNotification = jsonMapper.readValue(message, JMSNotification.class);

        // translate
        Notification notification = translate(jmsNotification);

        // forward to domain
        listener.onNotification(notification);

    }

    private Notification translate(JMSNotification jmsNotification) {
        return new Notification(jmsNotification.getMessage(), "JMS");
    }
}
