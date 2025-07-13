package com.arod.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Error> notFoundError(Exception ex, HttpServletRequest request){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND, request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> genericError(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .internalServerError()
                .body(buildError(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR
                        , request.getRequestURI()));
    }

    protected Error buildError(String message, HttpStatus status, String uri) {
        return new Error(message, status.value(), status.getReasonPhrase(), uri);
    }
}
