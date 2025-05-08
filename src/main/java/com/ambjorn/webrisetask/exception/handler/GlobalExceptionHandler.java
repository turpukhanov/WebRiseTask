package com.ambjorn.webrisetask.exception.handler;

import com.ambjorn.webrisetask.exception.SubscriptionNotFoundException;
import com.ambjorn.webrisetask.exception.UserDeleteException;
import com.ambjorn.webrisetask.exception.UserNotFoundException;
import com.ambjorn.webrisetask.exception.UserSaveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return handle(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDeleteException.class)
    public ResponseEntity<String> handleUserDelete(UserDeleteException ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<String> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        return handle(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserSaveException.class)
    public ResponseEntity<String> handleUserSave(UserSaveException ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return handle(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> handle(Exception ex, HttpStatus status) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(ex.getMessage());
    }


}
