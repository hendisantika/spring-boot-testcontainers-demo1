package com.hendisantika.routing;

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
public interface Endpoint {

    void send(Notification notification);
}
