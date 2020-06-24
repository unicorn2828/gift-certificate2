package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificatesDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Certificates;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.ICertificateService;
import com.epam.esm.validation.CertificateValidator;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService implements ICertificateService {
    static final Logger logger = LogManager.getLogger();
    private final ICertificateRepository certificateRepository;
    private final ITagRepository tagRepository;
    private final DtoMapper dtoMapper;

    @Override
    public CertificateDto findById(long id) {
        CertificateValidator.isId(id);
        Certificate certificate = certificateRepository.findById(id);
        certificate.setTags(tagRepository.findTagsByCertificateId(id));
        return dtoMapper.toCertificateDto(certificate);
    }

    @Override
    public CertificatesDto findAll() {
        Certificates certificates = new Certificates();
        certificates.setCertificates(certificateRepository.findAll());
        CertificatesDto certificatesDto = dtoMapper.toCertificatesDto(certificates);
        return certificatesDto;
    }

    @Override
    public CertificatesDto findAllCertificatesIncludeTags() {
        List<Certificate> certificateList = certificateRepository.findAll();
        certificateList.forEach(certificate -> certificate.setTags(tagRepository.
                findTagsByCertificateId(certificate.getId())));
        Certificates certificates = new Certificates();
        certificates.setCertificates(certificateList);
        return dtoMapper.toCertificatesDto(certificates);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        CertificateValidator.isId(id);
        certificateRepository.findById(id);
        certificateRepository.delete(id);
    }

    @Override
    @Transactional
    public CertificateDto create(CertificateDto certificateDto) {
        if (certificateDto == null) {
            ServiceErrorCode errorCode = ServiceErrorCode.CERTIFICATE_IS_NULL;
            logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
            throw new ServiceException(errorCode);
        }
        certificateDto.setCreationDate(LocalDate.now());
        certificateDto.setModificationDate(LocalDate.now());
        CertificateValidator.isCertificate(certificateDto);
        long certificateId;
        if (certificateDto.getTags() != null) {
            certificateDto.getTags().forEach(tag -> tag.setCertificates(null));
            certificateDto.getTags().forEach(TagValidator::isTag);
            certificateId = certificateRepository.create(dtoMapper.toCertificate(certificateDto));
            updateTags(certificateDto, certificateId);
        } else {
            certificateId = certificateRepository.create(dtoMapper.toCertificate(certificateDto));
        }
        return findById(certificateId);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(CertificateDto certificateDto) {
        if (certificateDto == null) {
            ServiceErrorCode errorCode = ServiceErrorCode.CERTIFICATE_IS_NULL;
            logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
            throw new ServiceException(errorCode);
        }
        long id = certificateDto.getId();
        CertificateValidator.isId(id);
        certificateDto.setModificationDate(LocalDate.now());
        CertificateValidator.isCertificate(certificateDto);
        Certificate oldCertificate = certificateRepository.findById(id);
        oldCertificate.setTags(tagRepository.findTagsByCertificateId(id));
        if (oldCertificate.getTags() != null) {
            oldCertificate.getTags().forEach(tag -> tagRepository.deleteTagFromTagCertificate(tag.getId()));
        }
        if (certificateDto.getTags() != null) {
            certificateDto.getTags().forEach(TagValidator::isTag);
            certificateRepository.updateCertificate(dtoMapper.toCertificate(certificateDto));
            updateTags(certificateDto, id);
        } else {
            certificateRepository.updateCertificate(dtoMapper.toCertificate(certificateDto));
        }
        return findById(id);
    }

    private void updateTags(CertificateDto certificateDto, long id) {
        for (TagDto tagDto : certificateDto.getTags()) {
            long tagId;
            if (tagRepository.isTagExist(tagDto.getName())) {
                List<Tag> tags = tagRepository.findTagByName(tagDto.getName());
                tagId = tags.stream()
                        .filter(tag -> tag.getName().equals(tagDto.getName()))
                        .findAny()
                        .orElse(null).getId();
            } else {
                tagId = tagRepository.create(dtoMapper.toTag(tagDto));
            }
            certificateRepository.addTagCertificate(tagId, id);
        }
    }
}
