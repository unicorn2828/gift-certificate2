package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.impl.CertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CertificateServiceTest {
    private CertificateService service = Mockito.mock(CertificateService.class);
    private static int TEST_CERTIFICATE_ID = 1;

    @Test
    void findCertificateById() {
       // Mockito.when(service.findById(TEST_CERTIFICATE_ID)).thenReturn(new CertificateDto());
        CertificateDto actual = service.findById(TEST_CERTIFICATE_ID);
        CertificateDto expected = new CertificateDto();
        Assertions.assertEquals(actual, expected);
    }

//    @Test
//    void findAllCertificates() {
//        Mockito.when(service.findAll()).thenReturn(new CertificatesDto());
//        CertificatesDto actual = service.findAll();
//        CertificatesDto expected = new CertificatesDto();
//        Assertions.assertEquals(actual, expected);
//    }

    @Test
    void removeCertificateById() {
    }


    @Test
    void createCertificate() {
    }

    @Test
    void updateCertificate() {
    }
}
