package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Certificates;
import com.epam.esm.model.Tags;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.ITagService;
import com.epam.esm.validation.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("tagService")
@RequiredArgsConstructor
public class TagService implements ITagService {
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
        tags.setTags(tagRepository.findAllTags());
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
            tagId = tagRepository.findTagByName(tagDto.getName()).getId();
        } else {
            tagId = tagRepository.create(dtoMapper.toTag(tagDto));
        }
        return findById(tagId);
    }

    @Override
    public TagDto findTagByName(String tagName) {
        TagValidator.isName(tagName);
        return dtoMapper.toTagDto(tagRepository.findTagByName(tagName));
    }

    @Override
    public TagsDto findTagByPartNAme(Map<String, String> allParams) {
        String partName = allParams.get(PARAM_NAME);
        Tags tags = new Tags();
        tags.setTags(tagRepository.findByPartName(partName));
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
