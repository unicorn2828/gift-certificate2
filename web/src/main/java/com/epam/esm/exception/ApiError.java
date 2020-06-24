package com.epam.esm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApiError {
    private String errorMessage;
    private String errorCode;
    private HttpStatus errorStatus;
    private List<String> errors;

    public ApiError(HttpStatus errorStatus, String errorMessage, List<String> errors) {
        super();
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public ApiError(HttpStatus errorStatus, String errorMessage, String errorCode) {
        super();
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    public ApiError(String errorMessage, String errorCode) {
        super();
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
