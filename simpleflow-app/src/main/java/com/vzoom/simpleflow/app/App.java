package com.vzoom.simpleflow.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vzoom.simpleflow"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}