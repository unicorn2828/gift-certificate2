package com.epam.esm.exception;

import com.epam.esm.exception.data.ErrorCode;
import com.epam.esm.exception.data.ErrorMessage;

public enum ServiceErrorCode {
    TAG_IS_NULL(ErrorCode.ERROR_000001, ErrorMessage.ERROR_000001),
    TAG_ID_LESS_THAN_1(ErrorCode.ERROR_000002, ErrorMessage.ERROR_000002),
    TAG_NAME_IS_NULL(ErrorCode.ERROR_000003, ErrorMessage.ERROR_000003),
    TAG_NAME_IS_EMPTY(ErrorCode.ERROR_000004, ErrorMessage.ERROR_000004),
    TAG_NAME_LESS_THAN_3(ErrorCode.ERROR_000005, ErrorMessage.ERROR_000005),
    TAG_NAME_MORE_THAN_30(ErrorCode.ERROR_000006, ErrorMessage.ERROR_000006),
    TAG_WITH_SUCH_NAME_NOT_EXISTS(ErrorCode.ERROR_000008, ErrorMessage.ERROR_000008),
    TAG_ID_IS_NULL(ErrorCode.ERROR_000009, ErrorMessage.ERROR_000009),

    CERTIFICATE_IS_NULL(ErrorCode.ERROR_000010, ErrorMessage.ERROR_000010),
    CERTIFICATE_ID_IS_NULL(ErrorCode.ERROR_000020, ErrorMessage.ERROR_000020),
    CERTIFICATE_ID_LESS_THAN_1(ErrorCode.ERROR_000021, ErrorMessage.ERROR_000021),
    CERTIFICATE_NAME_IS_NULL(ErrorCode.ERROR_000030, ErrorMessage.ERROR_000030),
    CERTIFICATE_NAME_IS_EMPTY(ErrorCode.ERROR_000031, ErrorMessage.ERROR_000031),
    CERTIFICATE_NAME_MORE_30(ErrorCode.ERROR_000032, ErrorMessage.ERROR_000032),
    CERTIFICATE_NAME_LESS_THAN_3(ErrorCode.ERROR_000033, ErrorMessage.ERROR_000033),
    CERTIFICATE_DESCRIPTION_IS_NULL(ErrorCode.ERROR_000040, ErrorMessage.ERROR_000040),
    CERTIFICATE_DESCRIPTION_LESS_THAN_3(ErrorCode.ERROR_000041, ErrorMessage.ERROR_000041),
    CERTIFICATE_DESCRIPTION_MORE_100(ErrorCode.ERROR_000042, ErrorMessage.ERROR_000042),
    CERTIFICATE_PRICE_LESS_THAN_0(ErrorCode.ERROR_000050, ErrorMessage.ERROR_000050),
    CERTIFICATE_PRICE_MORE_THAN_100(ErrorCode.ERROR_000051, ErrorMessage.ERROR_000051),
    CERTIFICATE_PRICE_IS_NULL(ErrorCode.ERROR_000052, ErrorMessage.ERROR_000052),
    DATE_CREATION_IS_NULL(ErrorCode.ERROR_000060, ErrorMessage.ERROR_000060),
    DATE_CREATION_AFTER_TODAY(ErrorCode.ERROR_000061, ErrorMessage.ERROR_000061),
    DATE_MODIFICATION_IS_NULL(ErrorCode.ERROR_000062, ErrorMessage.ERROR_000062),
    DATE_MODIFICATION_EARLY_THAN_TODAY(ErrorCode.ERROR_000063, ErrorMessage.ERROR_000063),
    CERTIFICATE_DURATION_LESS_THAN_1(ErrorCode.ERROR_000070, ErrorMessage.ERROR_000070),
    CERTIFICATE_DURATION_MORE_THAN_365(ErrorCode.ERROR_000071, ErrorMessage.ERROR_000071),
    CERTIFICATE_DURATION_IS_NULL(ErrorCode.ERROR_000072, ErrorMessage.ERROR_000072);


    private String errorCode;
    private String errorMessage;

    ServiceErrorCode(String errorId, String errorMessage) {
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
