package com.pierceecom.blog.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.pierceecom.blog.exception.ErrorDetails;
import com.pierceecom.blog.exception.PostException;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler {

    @ExceptionHandler(PostException.class)
    public final ResponseEntity<ErrorDetails> handlePostsException(final PostException ex, final WebRequest request) {
        final ErrorDetails errorDetails = ErrorDetails.builder() //
                .message(ex.getMessage()) //
                .timestamp(LocalDate.now()) //
                .details(request.getDescription(false)) //
                .build();

        return new ResponseEntity<>(errorDetails, ex.getHttpStatus());
    }
}
