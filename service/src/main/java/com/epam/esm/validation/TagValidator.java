package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.esm.exception.ServiceErrorCode.*;

public class TagValidator {
    static final Logger logger = LogManager.getLogger();
    private static final long MAX_NAME = 30;
    private static final long MIN_NAME = 3;

    public static boolean isTag(TagDto tag) {
        if (tag == null) {
            TagValidator.throwError(TAG_IS_NULL);
        }
        isName(tag.getName());
        return true;
    }

    public static boolean isId(Long id) {
        ServiceErrorCode errorCode = null;
        if (id == null) {
            errorCode = TAG_ID_IS_NULL;
        } else if (id < 1) {
            errorCode = TAG_ID_LESS_THAN_1;
        }
        if (errorCode != null) {
            TagValidator.throwError(errorCode);
        }
        return true;
    }

    public static boolean isName(String name) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (name == null) {
            errorCode = TAG_NAME_IS_NULL;
        } else if (name.isEmpty()) {
            errorCode = TAG_NAME_IS_EMPTY;
        } else if (name.length() > MAX_NAME) {
            errorCode = TAG_NAME_MORE_THAN_30;
        } else if (name.length() < MIN_NAME) {
            errorCode = TAG_NAME_LESS_THAN_3;
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
