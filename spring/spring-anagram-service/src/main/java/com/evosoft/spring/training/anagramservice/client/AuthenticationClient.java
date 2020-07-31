package com.evosoft.spring.training.anagramservice.client;

import com.evosoft.spring.training.anagramservice.configuration.FeignClientConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "authentication-service", configuration = FeignClientConfiguration.class)
public interface AuthenticationClient extends RemoteAccountController {
}
