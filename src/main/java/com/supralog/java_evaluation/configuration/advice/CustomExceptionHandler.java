package com.supralog.java_evaluation.configuration.advice;

import com.supralog.java_evaluation.configuration.exception.ElementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(ElementNotFoundException.class)
    public ProblemDetail handleElementNotFoundException(ElementNotFoundException ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        log.error(ex.getMessage());
        return errorDetail;
    }
}
