package com.master.authservice;

import com.master.authservice.exceptions.AccessForbiddenException;
import com.master.authservice.exceptions.AccessUnauthorizedException;
import com.master.authservice.exceptions.BadRequestException;
import com.master.authservice.exceptions.EntityNotFoundException;
import com.master.authservice.exceptions.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }

    @ExceptionHandler(value = AccessUnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handleAccessUnauthorizedException(AccessUnauthorizedException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleAccessForbiddenException(AccessForbiddenException ex) {

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.BAD_REQUEST, message);
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleUnmanagedExceptions(Exception ex) {
        LOGGER.error("Caught unknown exception", ex);
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ErrorMessage.ErrorType.INTERNAL_SERVER_ERROR, "Something went wrong");
        return new ResponseEntity<>(errorMessage, errorMessage.getStatus().getStatus());
    }
}
