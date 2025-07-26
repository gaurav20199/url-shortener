package com.project.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class InvalidUrl extends RuntimeException {
    private HttpStatus status;

    public InvalidUrl(HttpStatus status,String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
