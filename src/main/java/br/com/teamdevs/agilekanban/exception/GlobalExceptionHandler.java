package br.com.teamdevs.agilekanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        CustomException response = new CustomException(e.getStatus(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomException> handleConstraintViolation(ConstraintViolationException e) {
        throw new CustomException(HttpStatus.BAD_REQUEST.value(), "test...");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<UserNotFoundException> handleConstraintViolation(UserNotFoundException e) {
        throw new UserNotFoundException(HttpStatus.NOT_FOUND.value(), "test...");
    }
}
