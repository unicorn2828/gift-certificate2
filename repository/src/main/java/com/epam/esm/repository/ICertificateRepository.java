package com.epam.esm.repository;

import com.epam.esm.model.Certificate;

import java.util.List;

/**
 * The CertificateRepository  interface
 *
 * @author Vitaly Kononov
 */
public interface ICertificateRepository {

    /**
     * This method removes a certificate from database by id
     *
     * @param id - id of certificate
     * @return {link void}
     */
    void delete(long id);

    /**
     * This method finds a certificate in database by id
     *
     * @param id - id of certificate
     * @return {link} Certificate
     */
    Certificate findById(long id);

    /**
     * This method finds all certificates in database
     *
     * @return {link} List of certificates
     */
    List<Certificate> findAllCertificates();

    /**
     * This method finds certificates in database by id of the tag
     *
     * @param id - id of tag
     * @return {link} list of certificates
     */
    List<Certificate> findCertificatesByTagId(long id);

    /**
     * This method updates a certificate by id
     *
     * @param certificate - certificate for update
     * @return {link void}
     */
    void updateCertificate(Certificate certificate);

    /**
     * This method adds a tag-certificate to db
     *
     * @param tagId - tag id
     * @param certificateId - certificate id
     * @return {link void}
     */
    void addTagCertificate(long tagId, long certificateId);

    /**
     * This method adds new certificate to db
     *
     * @param certificate - new certificate
     * @return {link} id of certificate
     */
    long createCertificate(Certificate certificate);

    /**
     * This method injects ITagRepository entity to certificate repository
     *
     * @param tagRepository - ITagRepository entity
     * @return {link void}
     */
    void setTagRepository(ITagRepository tagRepository);
}
