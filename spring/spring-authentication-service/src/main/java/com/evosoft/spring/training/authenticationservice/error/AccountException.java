package com.evosoft.spring.training.authenticationservice.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AccountException extends Exception {
    public AccountException(final String msg) {
        super(msg);
    }
}
