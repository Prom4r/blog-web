package com.pierceecom.blog.exception;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
}
