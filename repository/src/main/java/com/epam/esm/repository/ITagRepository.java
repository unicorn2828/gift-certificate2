package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * The TagRepository  interface
 *
 * @author Vitaly Kononov
 */
public interface ITagRepository {

    /**
     * This method save new tag to db
     *
     * @param tag - the new tag to save
     * @return {link} id of tag
     */
    long create(Tag tag);

    /**
     * This method removes a tag by id
     *
     * @param id - id of tag
     * @return {link void}
     */
    void delete(long id);

    /**
     * This method removes a tag id and
     * certificate id from tag-certificate table
     *
     * @param id - id of tag
     * @return {link void}
     */
    void deleteTagFromTagCertificate(long id);

    /**
     * This method finds a tag in db by name
     * and returns 'true' if tag exists
     *
     * @param tagName - name of tag
     * @return {link} boolean value
     */
    boolean isTagExist(String tagName);

    /**
     * This method finds a tag in db by name
     *
     * @param tagName - name of tag
     * @return {link} Tag
     */
    Tag findTagByName(String tagName);

    /**
     * This method finds a tag in db by id
     *
     * @param id - id of tag
     * @return {link} Tag
     */
    Tag findById(long id);

    /**
     * This method finds all tags in db
     *
     * @return {link} list of tags
     */
    List<Tag> findAllTags();

    /**
     * This method finds a tag by part of name
     *
     * @param partName - tag name part
     * @return {link} list of tags
     */
    List<Tag> findByPartName(String partName);

    /**
     * This method finds a tag by certificate id
     *
     * @param id - id of certificate
     * @return {link} list of tags
     */
    List<Tag> findTagsByCertificateId(long id);
}
