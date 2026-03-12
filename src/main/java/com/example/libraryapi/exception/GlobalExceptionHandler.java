package com.example.libraryapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleBookNotFoundException(BookNotFoundException ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "One or more required fields are missing or invalid. Please check your request and try again.",
                request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMismatchedInputException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildProblem(
                HttpStatus.BAD_REQUEST,
                "Can't parse request body. Please ensure the JSON structure is correct and all required fields are provided.",
                request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknownException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        return buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request);
    }

    private ProblemDetail buildProblem(HttpStatus status, String message, HttpServletRequest request) {
        var problem = ProblemDetail.forStatus(status);
        problem.setProperty("timestamp", LocalDateTime.now().toString());
        problem.setProperty("error", status.getReasonPhrase());
        problem.setProperty("message", message);
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }
}
