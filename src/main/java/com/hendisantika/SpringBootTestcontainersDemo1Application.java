package com.hendisantika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SpringBootTestcontainersDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestcontainersDemo1Application.class, args);
    }

}
