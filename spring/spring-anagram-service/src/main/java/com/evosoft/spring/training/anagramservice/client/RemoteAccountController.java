package com.evosoft.spring.training.anagramservice.client;

import com.evosoft.spring.training.anagramservice.domain.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RemoteAccountController {
    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
    ResponseEntity<Account> findAccount(@PathVariable("username") String username);
}
