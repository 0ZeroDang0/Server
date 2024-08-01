package com.example.zerodang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ZeroDangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroDangApplication.class, args);
    }

}
