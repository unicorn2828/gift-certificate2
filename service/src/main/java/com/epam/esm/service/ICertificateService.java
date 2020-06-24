package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificatesDto;

/**
 * The ICertificateService interface
 *
 * @author Vitaly Kononov
 */
public interface ICertificateService extends IBaseService<CertificateDto, CertificatesDto> {

    /**
     * This method finds all certificates include tags in database
     *
     * @return {link} all certificates with tags
     */
    CertificatesDto findAllCertificatesIncludeTags();

    /**
     * This method updates a certificate
     *
     * @param certificateDto - the certificate to update
     * @return {link} certificate after updating
     */
    CertificateDto updateCertificate(CertificateDto certificateDto);
}
