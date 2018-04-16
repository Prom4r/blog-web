package com.pierceecom.blog.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class PostException extends Exception {

    private final HttpStatus httpStatus;

    private PostException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static PostException create(final String message, final HttpStatus httpStatus) {
        return new PostException(message, httpStatus);
    }
}
