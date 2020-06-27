package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificatesDto;
import com.epam.esm.service.ICertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8070")
@RequestMapping(value = "/certificates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    private final ICertificateService certificateService;

    /**
     * This method finds a certificate in database by id of the certificate
     *
     * @param id - id of certificate
     * @return {link} CertificateDTO
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto findCertificateById(@PathVariable("id") final Long id) {
        return certificateService.findById(id);
    }

    /**
     * This method finds all certificates in database
     *
     * @return {link} CertificatesDTO
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CertificatesDto findAllCertificates(@RequestParam Map<String, String> allParams) {
        return certificateService.findAll(allParams);
    }

    /**
     * This method finds all certificates include tags in database
     *
     * @return {link} CertificatesDTO
     */
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    public CertificatesDto findAllCertificatesIncludeTags() {
        return certificateService.findAllCertificatesIncludeTags();
    }

    /**
     * This method removes a certificate from database by id of the certificate
     *
     * @param id - id of certificate
     * @return {link void}
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCertificateById(@PathVariable("id") final Long id) {
        certificateService.delete(id);
    }

    /**
     * THis method creates a new certificate
     *
     * @param certificateDto - the new certificate to save
     * @return {link void}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CertificateDto createCertificate(@RequestBody(required = false) final CertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    /**
     * This method updates a certificate
     *
     * @param certificateDto - the certificate to update
     * @return {link void}
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CertificateDto updateCertificate(@PathVariable("id") final Long id, @RequestBody(required = false) final CertificateDto certificateDto) {
        return certificateService.updateCertificate(certificateDto, id);
    }
}
