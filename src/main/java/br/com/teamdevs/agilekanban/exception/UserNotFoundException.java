package br.com.teamdevs.agilekanban.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final int httpStatus;
    private final String message;

    public UserNotFoundException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    } 
}
