package com.hendisantika.outbond;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:53
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class RabbitMQConfiguration {

    @Bean
    public JacksonJsonMessageConverter producerJacksonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
