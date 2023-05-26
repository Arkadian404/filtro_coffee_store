package com.data.filtro.exception;

import java.util.List;

public class PasswordRuleException extends RuntimeException {

    private List<String> errorMessages;

    public PasswordRuleException(List<String> messages) {
        this.errorMessages = messages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

}
