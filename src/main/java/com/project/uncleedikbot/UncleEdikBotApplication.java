package com.project.uncleedikbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UncleEdikBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(UncleEdikBotApplication.class, args);
    }

}
