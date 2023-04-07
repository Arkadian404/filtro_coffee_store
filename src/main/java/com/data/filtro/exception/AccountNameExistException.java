package com.data.filtro.exception;

public class AccountNameExistException extends RuntimeException {
    public AccountNameExistException(String message) {
        super(message);
    }
}
