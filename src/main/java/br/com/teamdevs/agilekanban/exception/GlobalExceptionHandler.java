package br.com.teamdevs.agilekanban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomException> handleCustomException(CustomException e) {
        CustomException response = new CustomException(e.getTimestamp(), e.getStatus(), e.getMessage(), e.getError());
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}
