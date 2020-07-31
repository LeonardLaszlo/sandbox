package com.evosoft.spring.training.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
class AuthenticationService {
    public static void main(final String[] args) {
        SpringApplication.run(AuthenticationService.class, args);
    }
}
