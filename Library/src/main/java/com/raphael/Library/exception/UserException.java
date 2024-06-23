package com.raphael.Library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends Exception {

    private HttpStatus status;

    public UserException(String error, HttpStatus status) {
        super(error);
        this.status = status;
    }
}