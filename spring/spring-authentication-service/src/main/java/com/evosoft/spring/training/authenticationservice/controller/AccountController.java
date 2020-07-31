package com.evosoft.spring.training.authenticationservice.controller;

import com.evosoft.spring.training.authenticationservice.domain.Account;
import com.evosoft.spring.training.authenticationservice.domain.CustomErrorType;
import com.evosoft.spring.training.authenticationservice.error.AccountException;
import com.evosoft.spring.training.authenticationservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public final class AccountController {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());
    private final AccountService service;

    @Autowired
    private AccountController(final AccountService accountService) {
        this.service = accountService;
    }

    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
    public ResponseEntity findAccount(@PathVariable("username") final String username) {
        try {
            final Account account = service.findAccountByUserName(username);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (AccountException ex) {
            LOGGER.severe(ex.getMessage());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity saveAccount(@RequestBody final Account account) {
        try {
            service.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            LOGGER.severe(ex.toString());
            return new ResponseEntity<>(new CustomErrorType(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
