package com.hendisantika.routing;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:57
 * To change this template use File | Settings | File Templates.
 */
@Component
public class NotificationRouter implements NotificationListener {

    private final List<Endpoint> endpoints;
    private final NotificationRepository notificationRepository;

    public NotificationRouter(List<Endpoint> endpoints, NotificationRepository notificationRepository) {
        super();
        this.endpoints = endpoints;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onNotification(Notification notification) {

        // save before forwarding
        notificationRepository.save(notification);

        // forward event to registered endpoints
        for (Endpoint endpoint : endpoints) {
            endpoint.send(notification);
        }

    }
}
