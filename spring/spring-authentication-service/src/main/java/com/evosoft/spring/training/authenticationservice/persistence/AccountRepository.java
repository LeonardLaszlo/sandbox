package com.evosoft.spring.training.authenticationservice.persistence;

import com.evosoft.spring.training.authenticationservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String userName);
}
