package com.Famto.Famto.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        logger.error("Bad credentials exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.FORBIDDEN, "The username or password is incorrect", exception);
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleAccountStatusException(AccountStatusException exception, WebRequest request) {
        logger.error("Account status exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.FORBIDDEN, "The account is locked", exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        logger.error("Access denied exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.FORBIDDEN, "You are not authorized to access this resource", exception);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleSignatureException(SignatureException exception, WebRequest request) {
        logger.error("Signature exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.FORBIDDEN, "The JWT signature is invalid", exception);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException exception, WebRequest request) {
        logger.error("Expired JWT exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.FORBIDDEN, "The JWT token has expired", exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleGeneralException(Exception exception, WebRequest request) {
        logger.error("General exception: {}", exception.getMessage(), exception);
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown internal server error", exception);
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String description, Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        errorDetail.setProperty("description", description);
        return errorDetail;
    }
}
