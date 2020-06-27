package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificatesDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Certificates;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.ICertificateService;
import com.epam.esm.service.ITagService;
import com.epam.esm.validation.CertificateValidator;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CertificateService implements ICertificateService {
    static final Logger logger = LogManager.getLogger();
    private static final String PARAM_TAG_NAME = "tagName";
    private static final String PARAM_CERTIFICATE_NAME = "certificateName";
    private final ICertificateRepository certificateRepository;
    private final ITagRepository tagRepository;
    private final ITagService tagService;
    private final DtoMapper dtoMapper;

    @Override
    public CertificateDto findById(long id) {
        CertificateValidator.isId(id);
        Certificate certificate = certificateRepository.findById(id);
        certificate.setTags(tagRepository.findTagsByCertificateId(id));
        return dtoMapper.toCertificateDto(certificate);
    }

    @Override
    public CertificatesDto findAll(Map<String, String> allParams) {
        CertificatesDto certificatesDto = null;
        if (allParams.isEmpty()) {
            Certificates certificates = new Certificates();
            certificates.setCertificates(certificateRepository.findAll());
            certificatesDto = dtoMapper.toCertificatesDto(certificates);
        } else {
            for (String param : allParams.keySet()) {
                switch (param) {
                    case PARAM_CERTIFICATE_NAME:
                        String certificateName = allParams.get(param);
                        certificatesDto = findByName(certificateName);
                        break;
                    case PARAM_TAG_NAME:
                        String tagName = allParams.get(param);
                        certificatesDto = findByTagId(tagName);
                        break;
                    default:
                        ServiceErrorCode errorCode = ServiceErrorCode.UNKNOWN_PARAMETER;
                        logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
                        throw new ServiceException(errorCode);
                }
            }
        }
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
    public void delete(long id) {
        CertificateValidator.isId(id);
        certificateRepository.findById(id);
        certificateRepository.delete(id);
    }

    @Override
    @Transactional
    public CertificateDto create(CertificateDto certificateDto) {
        CertificateValidator.isCertificate(certificateDto);
        certificateDto.setCreationDate(LocalDate.now());
        certificateDto.setModificationDate(LocalDate.now());
        CertificateValidator.isDate(certificateDto.getCreationDate(), certificateDto.getModificationDate());
        long certificateId;
        if (certificateDto.getTags() != null) {
            certificateDto.getTags().forEach(tag -> tag.setCertificates(null));
            certificateDto.getTags().forEach(TagValidator::isTag);
            certificateId = certificateRepository.create(dtoMapper.toCertificate(certificateDto));
            updateTagsOfCertificate(certificateDto, certificateId);
        } else {
            certificateId = certificateRepository.create(dtoMapper.toCertificate(certificateDto));
        }
        return findById(certificateId);
    }

    @Override
    @Transactional
    public CertificateDto updateCertificate(CertificateDto certificateDto, Long id) {
        CertificateValidator.isId(id);
        certificateDto.setId(id);
        CertificateValidator.isCertificate(certificateDto);
        certificateDto.setModificationDate(LocalDate.now());
        CertificateValidator.isDate(certificateDto.getCreationDate(), certificateDto.getModificationDate());
        Certificate oldCertificate = certificateRepository.findById(id);
        oldCertificate.setTags(tagRepository.findTagsByCertificateId(id));
        oldCertificate.getTags().forEach(tag -> tagRepository.deleteTagFromTagCertificate(id));
        if (certificateDto.getTags() != null) {
            certificateDto.getTags().forEach(TagValidator::isTag);
            tagRepository.deleteTagFromTagCertificate(certificateDto.getId());
            certificateRepository.updateCertificate(dtoMapper.toCertificate(certificateDto));
            updateTagsOfCertificate(certificateDto, id);
        } else {
            certificateRepository.updateCertificate(dtoMapper.toCertificate(certificateDto));
        }
        return findById(id);
    }

    @Transactional
    public void updateTagsOfCertificate(CertificateDto certificateDto, long id) {
        for (TagDto tagDto : certificateDto.getTags()) {
            long tagId;
            if (tagRepository.isTagExist(tagDto.getName())) {
                tagId = tagService.findTagIdByTagName(tagDto.getName());
            } else {
                tagId = tagRepository.create(dtoMapper.toTag(tagDto));
            }
            certificateRepository.addTagCertificate(tagId, id);
        }
    }

    @Override
    public CertificatesDto findByName(String certificateName) {
        CertificateValidator.isName(certificateName);
        Certificates certificates = new Certificates();
        certificates.setCertificates(certificateRepository.findByName(certificateName));
        if (certificates.getCertificates().isEmpty()) {
            ServiceErrorCode errorCode = ServiceErrorCode.CERTIFICATE_WITH_SUCH_NAME_NOT_EXISTS;
            logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
            throw new ServiceException(errorCode);
        }
        return dtoMapper.toCertificatesDto(certificates);
    }

    private CertificatesDto findByTagId(String tagName) {
        long tagId = tagService.findTagIdByTagName(tagName);
        Certificates certificates = new Certificates();
        certificates.setCertificates(certificateRepository.findCertificatesByTagId(tagId));
        return dtoMapper.toCertificatesDto(certificates);
    }
}
