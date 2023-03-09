package com.hendisantika.routing;

import org.springframework.data.jpa.repository.JpaRepository;

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
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
