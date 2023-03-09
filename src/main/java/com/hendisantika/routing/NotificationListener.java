package com.hendisantika.routing;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:56
 * To change this template use File | Settings | File Templates.
 */

/**
 * NotificationListener listens for notifications
 */
public interface NotificationListener {

    /**
     * Called when {@link Notification} objects are received by the application
     */
    void onNotification(Notification notification);
}
