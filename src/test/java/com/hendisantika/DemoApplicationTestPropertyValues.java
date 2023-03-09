package com.hendisantika;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 06:03
 * To change this template use File | Settings | File Templates.
 */
public class DemoApplicationTestPropertyValues {
    public static void populateRegistryFromContainers(DynamicPropertyRegistry registry,
                                                      PostgreSQLContainer<?> postgreSQLContainer,
                                                      GenericContainer<?> activeMQContainer,
                                                      GenericContainer<?> rabbitMQContainer) {

        populateRegistryFromPostgresContainer(registry, postgreSQLContainer);

        // activemq
        registry.add("spring.activemq.broker-url", () -> "tcp://localhost:" + activeMQContainer.getMappedPort(61616));

        // rabbitmq
        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));

    }

    public static void populateRegistryFromPostgresContainer(DynamicPropertyRegistry registry,
                                                             PostgreSQLContainer<?> postgreSQLContainer) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
