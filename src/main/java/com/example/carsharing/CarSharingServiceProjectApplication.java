package com.example.carsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarSharingServiceProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarSharingServiceProjectApplication.class, args);
    }
}
