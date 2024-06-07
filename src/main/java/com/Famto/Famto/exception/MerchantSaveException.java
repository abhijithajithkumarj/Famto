package com.Famto.Famto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MerchantSaveException extends RuntimeException {
    public MerchantSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
