package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class EvilTouristBotApplication {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(EvilTouristBotApplication.class, args);
    }
}
