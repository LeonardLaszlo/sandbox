package com.evosoft.spring.training.authenticationservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class CustomErrorType {
    private String errorMessage;

    public CustomErrorType(final String message) {
        this.errorMessage = message;
    }
}
