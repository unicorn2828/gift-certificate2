package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.exception.ServiceException;
import org.junit.After;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.gen5.api.Assertions.assertTrue;

class CertificateValidatorTest {
    private static final String TEST = "test";
    private CertificateDto expected;

    @BeforeEach
    void setUp() {
        expected = new CertificateDto();
        expected.setId(1L);
        expected.setCertificateName(TEST);
        expected.setDescription(TEST);
        expected.setPrice(new BigDecimal(0));
        expected.setCreationDate(LocalDate.now());
        expected.setModificationDate(LocalDate.now());
        expected.setDuration(60);
    }

    @After
    public void tierDown() {
        expected = null;
    }

    @Test
    void isCertificate() {
        boolean actual = CertificateValidator.isCertificate(expected);
        assertTrue(actual);
    }

    @Test
    void isCertificateNegativeTestOneFieldNull() {
        expected.setCertificateName(null);
        Assertions.assertThrows(ServiceException.class, () -> {
            CertificateValidator.isCertificate(expected);
        });
    }

    @Test
    void isCertificateNegativeNull() {
        expected = null;
        Assertions.assertThrows(ServiceException.class, () -> {
            CertificateValidator.isCertificate(expected);
        });
    }
}
