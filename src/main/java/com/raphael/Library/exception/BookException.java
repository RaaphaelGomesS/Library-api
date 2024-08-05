package com.raphael.Library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookException extends Exception {

    private HttpStatus status;

    public BookException(String error, HttpStatus status) {
        super(error);
        this.status = status;
    }
}
