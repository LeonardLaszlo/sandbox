package com.evosoft.spring.training.authenticationservice.service;

import com.evosoft.spring.training.authenticationservice.domain.Account;
import com.evosoft.spring.training.authenticationservice.error.AccountException;
import com.evosoft.spring.training.authenticationservice.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());

    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountByUserName(final String userName) throws AccountException {
        return this.accountRepository
                .findByUsername(userName)
                .orElseThrow(() -> new AccountException("Username is not registered!"));
    }

    public void saveAccount(final Account account) throws AccountException {
        final Optional<Account> foundAccount = this.accountRepository.findByUsername("accountuser");
        if (foundAccount.isPresent()) {
            throw new AccountException("Username is already registered!");
        } else {
            this.accountRepository.save(account);
        }
    }
}
