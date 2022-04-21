package com.master.authservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private Date timestamp;
    private ErrorType status;
    private String message;

    public enum ErrorType {
        NOT_FOUND(HttpStatus.NOT_FOUND),
        BAD_REQUEST(HttpStatus.BAD_REQUEST),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
        FORBIDDEN(HttpStatus.FORBIDDEN);

        @Getter
        private final HttpStatus status;

        ErrorType(HttpStatus status) {
            this.status = status;
        }
    }
}
