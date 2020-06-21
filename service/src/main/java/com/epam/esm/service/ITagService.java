package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;

import java.util.Map;

/**
 * The ITagService interface
 *
 * @author Vitaly Kononov
 */
public interface ITagService extends IBaseService<TagDto, TagsDto> {

    /**
     * This method finds a tag by id
     * include certificates field
     *
     * @param id - id of tag
     * @return {link} TagDTO
     */
    TagDto findTagByIdIncludeCertificates(long id);

    /**
     * This method finds a tag by name
     *
     * @param tagName - name of tag
     * @return {link} TagDTO
     */
    TagDto findTagByName(String tagName);

    /**
     * This method finds a tag by part of name
     *
     * @param allParams - tag name part
     * @return {link} TagsDTO
     */
    TagsDto findTagByPartNAme(Map<String, String> allParams);
}
