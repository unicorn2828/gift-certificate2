package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;
import com.epam.esm.exception.ServiceErrorCode;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Certificates;
import com.epam.esm.model.Tag;
import com.epam.esm.model.Tags;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.ITagService;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("tagService")
@RequiredArgsConstructor
public class TagService implements ITagService {
    static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME = "tagName";
    private final ICertificateRepository certificateRepository;
    private final ITagRepository tagRepository;
    private final DtoMapper dtoMapper;

    @Override
    public TagDto findById(long id) {
        TagValidator.isId(id);
        return dtoMapper.toTagDto(tagRepository.findById(id));
    }

    @Override
    public TagDto findTagByIdIncludeCertificates(long id) {
        TagValidator.isId(id);
        TagDto tagDto = dtoMapper.toTagDto(tagRepository.findById(id));
        Certificates certificates = new Certificates();
        certificates.setCertificates(certificateRepository.findCertificatesByTagId(id));
        tagDto.setCertificates(dtoMapper.toCertificatesDto(certificates).getCertificates());
        TagValidator.isTag(tagDto);
        return tagDto;
    }

    @Override
    public TagsDto findAll() {
        Tags tags = new Tags();
        tags.setTags(tagRepository.findAll());
        TagsDto tagsDto = dtoMapper.toTagsDto(tags);
        tagsDto.getTags().stream().forEach(TagValidator::isTag);
        return tagsDto;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        TagValidator.isTag(tagDto);
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
        return findById(tagId);
    }

    @Override
    public TagsDto findTagByName(Map<String, String> allParams) {
        String tagName = allParams.get(PARAM_NAME);
        TagValidator.isName(tagName);
        Tags tags = new Tags();
        tags.setTags(tagRepository.findTagByName(tagName));
        if (tags.getTags().isEmpty()) {
            ServiceErrorCode errorCode = ServiceErrorCode.TAG_WITH_SUCH_NAME_NOT_EXISTS;
            logger.error(errorCode.getErrorCode() + ":" + errorCode.getErrorMessage());
            throw new ServiceException(errorCode);
        }
        return dtoMapper.toTagsDto(tags);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        TagValidator.isId(id);
        tagRepository.findById(id);
        tagRepository.delete(id);
    }
}
