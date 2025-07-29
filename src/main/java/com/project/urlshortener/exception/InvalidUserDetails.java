package com.project.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class InvalidUserDetails extends RuntimeException {
    private HttpStatus httpStatus;

    public InvalidUserDetails(HttpStatus httpStatus,String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
