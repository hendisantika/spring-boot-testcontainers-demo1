package com.hendisantika.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.NotificationListener;

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
}
