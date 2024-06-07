package com.Famto.Famto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AdminSaveException extends RuntimeException {
    public AdminSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
