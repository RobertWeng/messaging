package com.weng.messaging.exception;

import com.weng.messaging.exception.Error.Code;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ControllerAdvice
@Slf4j
public class GlobalAdvice {

    @ExceptionHandler(Catch.class)
    public ResponseEntity<Catch> handleCatch(HttpServletRequest req, Catch e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Catch> handleException(HttpServletRequest req, Exception e) {
        log.error("Error: {}", getStackTrace(e));
        return handleCatch(req, new Catch(HttpStatus.INTERNAL_SERVER_ERROR, Code.INTERNAL_SERVER_ERROR, Error.Msg.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Catch> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
        return handleCatch(req, new Catch(HttpStatus.BAD_REQUEST, Code.INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Catch> handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        return handleCatch(req, new Catch(HttpStatus.BAD_REQUEST, Code.INVALID_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Catch> handleNoHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
        return handleCatch(req, new Catch(HttpStatus.NOT_FOUND, Code.NOT_FOUND, e.getMessage()));
    }
}
