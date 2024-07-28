package com.example.zerodang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZeroDangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeroDangApplication.class, args);
    }

}
