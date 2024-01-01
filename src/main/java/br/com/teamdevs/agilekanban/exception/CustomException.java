package br.com.teamdevs.agilekanban.exception;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String error;
}
