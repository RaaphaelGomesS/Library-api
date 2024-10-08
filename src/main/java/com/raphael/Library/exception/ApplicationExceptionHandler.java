package com.raphael.Library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AssociateException.class)
    public ResponseEntity handlerAssociateException(AssociateException e) {

        AssociateException exception = new AssociateException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity handlerBookException(BookException e) {
        log.info("error: " + e.getMessage());

        BookException exception = new BookException(e.getMessage(), e.getStatus());

        return new ResponseEntity(exception.getMessage(), e.getStatus());
    }

    @ExceptionHandler(RequisitionException.class)
    public ResponseEntity handlerRequisitionException(RequisitionException e) {
        log.info("error: " + e.getMessage());

        RequisitionException exception = new RequisitionException(e.getMessage(), e.getStatus());

        return new ResponseEntity(exception.getMessage(), e.getStatus());
    }
}
