package com.hendisantika.routing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo1
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/10/23
 * Time: 05:55
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_id_generator", sequenceName = "notification_id_sequence", allocationSize
            = 1)
    @GeneratedValue(generator = "notification_id_generator")
    private Long id;
    private String message;
    private String source;

    public Notification(String message, String source) {
        this.message = message;
        this.source = source;
    }
}
