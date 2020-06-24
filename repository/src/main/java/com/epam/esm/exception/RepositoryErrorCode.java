package com.epam.esm.exception;

import com.epam.esm.exception.data.ErrorCode;
import com.epam.esm.exception.data.ErrorMessage;

public enum RepositoryErrorCode {
    TAG_WITH_SUCH_ID_NOT_EXIST(ErrorCode.ERROR_000001, ErrorMessage.ERROR_000001),
    CERTIFICATE_WITH_SUCH_ID_NOT_EXISTS(ErrorCode.ERROR_000002, ErrorMessage.ERROR_000002);

    private String errorCode;
    private String errorMessage;

    RepositoryErrorCode(String errorId, String errorMessage) {
        this.errorCode = errorId;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
