package com.raphael.Library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequisitionException extends Exception {

    private HttpStatus status;

    public RequisitionException(String error, HttpStatus status) {
        super(error);
        this.status = status;
    }
}
