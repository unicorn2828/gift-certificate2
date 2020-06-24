package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagValidator {
    static final Logger logger = LogManager.getLogger();

    public static boolean isTag(TagDto tag) {
        if (tag == null) {
            TagValidator.throwError(ServiceErrorCode.TAG_IS_NULL);
        }
        isName(tag.getName());
        return true;
    }

    public static boolean isId(Long id) {
        ServiceErrorCode errorCode = null;
        if (id == null) {
            errorCode = ServiceErrorCode.TAG_ID_IS_NULL;
        } else if (id < 1) {
            errorCode = ServiceErrorCode.TAG_ID_LESS_THAN_1;
        }
        if (errorCode != null) {
            TagValidator.throwError(errorCode);
        }
        return true;
    }

    public static boolean isName(String name) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (name == null) {
            errorCode = ServiceErrorCode.TAG_NAME_IS_NULL;
        } else if (name.isEmpty()) {
            errorCode = ServiceErrorCode.TAG_NAME_IS_EMPTY;
        } else if (name.length() > 30) {
            errorCode = ServiceErrorCode.TAG_NAME_MORE_THAN_30;
        } else if (name.length() < 3) {
            errorCode = ServiceErrorCode.TAG_NAME_LESS_THAN_3;
        }
        if (errorCode != null) {
            TagValidator.throwError(errorCode);
        }
        return true;
    }

    private static void throwError(ServiceErrorCode errorCode) {
        logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
        throw new ServiceException(errorCode);
    }
}
