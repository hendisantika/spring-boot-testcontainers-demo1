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
public class RoutingNotificationQueryService implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public RoutingNotificationQueryService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }
}
