package com.raphael.Library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AssociateException extends Exception {

    private HttpStatus status;

    public AssociateException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
