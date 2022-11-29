package com.sparta.hanghaememo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaememoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaememoApplication.class, args);
    }

}
