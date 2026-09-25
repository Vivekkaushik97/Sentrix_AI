package com.sentrix.ai.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(com.sentrix.ai.common.exception.ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(com.sentrix.ai.common.exception.ResourceNotFoundException ex) {
        String correlationId = MDC.get("correlationId");
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                correlationId
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex) {
        String correlationId = MDC.get("correlationId");
        logger.error("Unhandled exception: ", ex);
        
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred.",
                correlationId
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
