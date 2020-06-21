package com.epam.esm.exception;

public class ServiceException extends RuntimeException {
    protected String errorCode;
    protected String errorMessage;

    public ServiceException() {
        super();
    }

    public ServiceException(final Throwable e) {
        super(e);
    }

    public ServiceException(final String errorCode, final Throwable e) {
        super(errorCode, e);
    }

    public ServiceException(final String errorCode, final String errorMessage) {
        super(errorCode);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ServiceException(final ServiceErrorCode errorCode) {
        this(errorCode.getErrorCode(), errorCode.getErrorMessage());
        this.errorMessage = errorCode.getErrorMessage();
        this.errorCode = errorCode.getErrorCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
