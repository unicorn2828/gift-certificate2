package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;

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
     * This method finds all tags include certificates field
     *
     * @return {link} TagsDTO
     */
    TagsDto findAllTagsIncludeCertificates();

    /**
     * This method finds a tag id by tag name
     *
     * @param tagName - name of tag
     * @return {link} long - id of tag
     */
    long findTagIdByTagName(String tagName);
}
