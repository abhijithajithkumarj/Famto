package com.Famto.Famto.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MerchantDeleteException extends RuntimeException {
    public MerchantDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

