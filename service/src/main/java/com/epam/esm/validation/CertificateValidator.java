package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CertificateValidator {
    static final Logger logger = LogManager.getLogger();

    public static boolean isCertificate(CertificateDto certificate) {
        if (certificate == null) {
            CertificateValidator.throwError(ServiceErrorCode.CERTIFICATE_IS_NULL);
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
            errorCode = ServiceErrorCode.CERTIFICATE_ID_IS_NULL;
        } else if (id < 1) {
            errorCode = ServiceErrorCode.CERTIFICATE_ID_LESS_THAN_1;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isName(String name) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (name == null) {
            errorCode = ServiceErrorCode.CERTIFICATE_NAME_IS_NULL;
        } else if (name.isEmpty()) {
            errorCode = ServiceErrorCode.CERTIFICATE_NAME_IS_EMPTY;
        } else if (name.length() > 30) {
            errorCode = ServiceErrorCode.CERTIFICATE_NAME_MORE_30;
        } else if (name.length() < 3) {
            errorCode = ServiceErrorCode.CERTIFICATE_NAME_LESS_THAN_3;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDescription(String description) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (description == null) {
            errorCode = ServiceErrorCode.CERTIFICATE_DESCRIPTION_IS_NULL;
        } else if (description.length() > 100) {
            errorCode = ServiceErrorCode.CERTIFICATE_DESCRIPTION_MORE_100;
        } else if (description.length() > 0 && description.length() < 3) {
            errorCode = ServiceErrorCode.CERTIFICATE_DESCRIPTION_LESS_THAN_3;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isPrice(BigDecimal price) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (price == null) {
            errorCode = ServiceErrorCode.CERTIFICATE_PRICE_IS_NULL;
        } else if (price.compareTo(new BigDecimal(100)) > 0) {
            errorCode = ServiceErrorCode.CERTIFICATE_PRICE_MORE_THAN_100;
        } else if (price.compareTo(new BigDecimal(0)) < 0) {
            errorCode = ServiceErrorCode.CERTIFICATE_PRICE_LESS_THAN_0;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDate(LocalDate creationDate, LocalDate modificationDate) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (creationDate == null) {
            errorCode = ServiceErrorCode.DATE_CREATION_IS_NULL;
        } else if (creationDate.isAfter(LocalDate.now())) {
            errorCode = ServiceErrorCode.DATE_CREATION_AFTER_TODAY;
        }
        if (modificationDate == null) {
            errorCode = ServiceErrorCode.DATE_MODIFICATION_IS_NULL;
        } else if (modificationDate.isBefore(LocalDate.now())) {
            errorCode = ServiceErrorCode.DATE_MODIFICATION_EARLY_THAN_TODAY;
        }
        if (errorCode != null) {
            CertificateValidator.throwError(errorCode);
        }
        return true;
    }

    private static boolean isDuration(Integer duration) throws ServiceException {
        ServiceErrorCode errorCode = null;
        if (duration == null) {
            errorCode = ServiceErrorCode.CERTIFICATE_DURATION_IS_NULL;
        } else if (duration > 365) {
            errorCode = ServiceErrorCode.CERTIFICATE_DURATION_MORE_THAN_365;
        } else if (duration < 1) {
            errorCode = ServiceErrorCode.CERTIFICATE_DURATION_LESS_THAN_1;
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
