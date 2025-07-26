package com.project.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class ExpiredUrl extends RuntimeException {

    private HttpStatus status;

    public ExpiredUrl(HttpStatus httpStatus,String message) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
