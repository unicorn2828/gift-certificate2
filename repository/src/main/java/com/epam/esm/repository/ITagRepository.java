package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * The TagRepository  interface
 *
 * @author Vitaly Kononov
 */
public interface ITagRepository extends IBaseRepository<Tag> {

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
     * This method finds a tag by certificate id
     *
     * @param id - id of certificate
     * @return {link} list of tags
     */
    List<Tag> findTagsByCertificateId(long id);
}
