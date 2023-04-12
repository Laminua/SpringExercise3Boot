package com.example.springexercise3boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringExercise3BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExercise3BootApplication.class, args);
    }

}
