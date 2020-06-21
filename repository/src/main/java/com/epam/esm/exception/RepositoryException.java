package com.epam.esm.exception;

public class RepositoryException extends RuntimeException {
    protected String errorCode;
    protected String errorMessage;

    public RepositoryException() {
        super();
    }

    public RepositoryException(final Throwable e) {
        super(e);
    }

    public RepositoryException(final String errorCode, final Throwable e) {
        super(errorCode, e);
    }

    public RepositoryException(final String errorCode, final String errorMessage) {
        super(errorCode);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public RepositoryException(final RepositoryErrorCode errorCode) {
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
