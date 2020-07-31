package com.evosoft.spring.training.anagramservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
class AnagramService {
    public static void main(final String[] args) {
        SpringApplication.run(AnagramService.class, args);
    }
}
