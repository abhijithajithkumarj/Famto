package com.Famto.Famto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUUIDFormatException extends RuntimeException {
    public InvalidUUIDFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
