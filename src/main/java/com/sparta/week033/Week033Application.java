package com.sparta.week033;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Week033Application {

    public static void main(String[] args) {
        SpringApplication.run(Week033Application.class, args);
    }

}