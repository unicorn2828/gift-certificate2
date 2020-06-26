package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.epam.esm.exception.ServiceErrorCode.*;

public class CertificateValidator {
    static final Logger logger = LogManager.getLogger();
    private static final long MAX_DURATION = 365;
    private static final long MIN_DURATION = 1;
    private static final long MAX_PRICE = 100;
    private static final long MIN_PRICE = 1;
    private static final long MAX_DESCRIPTION = 100;
    private static final long MIN_DESCRIPTION = 3;
    private static final long MAX_NAME = 30;
    private static final long MIN_NAME = 3;

    public static boolean isCertificate(CertificateDto certificate) {
        if (certificate == null) {
            CertificateValidator.throwError(CERTIFICATE_IS_NULL);
        }
        CertificateValidator.isName(certificate.getCertificateName());
        CertificateValidator.isDescription(certificate.getDescription());
        CertificateValidator.isPrice(certificate.getPrice());
        CertificateValidator.isDate(certificate.getCreationDate(), certificate.getModificationDate());
        CertificateValidator.isDuration(certificate.getDuration());
        if (certificate.getTags() != null) {
            certificate.getTags().stream().allMatch(TagValidator::isTag);
        }
        return true;
    }

    public static boolean isId(Long id) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (id == null) {
            errorCode = CERTIFICATE_ID_IS_NULL;
        } else if (id < 1) {
            errorCode = CERTIFICATE_ID_LESS_THAN_1;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    public static boolean isName(String name) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (name == null) {
            errorCode = CERTIFICATE_NAME_IS_NULL;
        } else if (name.isEmpty()) {
            errorCode = CERTIFICATE_NAME_IS_EMPTY;
        } else if (name.length() > MAX_NAME) {
            errorCode = CERTIFICATE_NAME_MORE_30;
        } else if (name.length() < MIN_NAME) {
            errorCode = CERTIFICATE_NAME_LESS_THAN_3;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDescription(String description) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (description == null) {
            errorCode = CERTIFICATE_DESCRIPTION_IS_NULL;
        } else if (description.length() > MAX_DESCRIPTION) {
            errorCode = CERTIFICATE_DESCRIPTION_MORE_100;
        } else if (description.length() > 0 && description.length() < MIN_DESCRIPTION) {
            errorCode = CERTIFICATE_DESCRIPTION_LESS_THAN_3;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isPrice(BigDecimal price) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (price == null) {
            errorCode = CERTIFICATE_PRICE_IS_NULL;
        } else if (price.compareTo(new BigDecimal(MAX_PRICE)) > 0) {
            errorCode = CERTIFICATE_PRICE_MORE_THAN_100;
        } else if (price.compareTo(new BigDecimal(MIN_PRICE)) < 0) {
            errorCode = CERTIFICATE_PRICE_LESS_THAN_0;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDate(LocalDate creationDate, LocalDate modificationDate) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (creationDate == null) {
            errorCode = DATE_CREATION_IS_NULL;
        } else if (creationDate.isAfter(LocalDate.now())) {
            errorCode = DATE_CREATION_AFTER_TODAY;
        }
        if (modificationDate == null) {
            errorCode = DATE_MODIFICATION_IS_NULL;
        } else if (modificationDate.isBefore(LocalDate.now())) {
            errorCode = DATE_MODIFICATION_EARLY_THAN_TODAY;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDuration(Integer duration) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (duration == null) {
            errorCode = CERTIFICATE_DURATION_IS_NULL;
        } else if (duration > MAX_DURATION) {
            errorCode = CERTIFICATE_DURATION_MORE_THAN_365;
        } else if (duration < MIN_DURATION) {
            errorCode = CERTIFICATE_DURATION_LESS_THAN_1;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static void throwError(ServiceErrorCode errorCode) {
        logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
        throw new ServiceException(errorCode);
    }
}
