package com.epam.esm.repository.impl;

import com.epam.esm.exception.RepositoryErrorCode;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.ITagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import static com.epam.esm.repository.data.CertificateRepositoryData.*;

@Repository("certificateRepository")
@RequiredArgsConstructor
public class CertificateRepository implements ICertificateRepository {
    static final Logger logger = LogManager.getLogger();
    private RepositoryErrorCode errorCode = null;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CertificateRowMapper mapper;
    private final JdbcTemplate jdbcTemplate;
    private ITagRepository tagRepository;
    private final KeyHolder keyHolder;

    @Override
    public Certificate findById(long id) throws RepositoryException {
        try {
            Certificate certificate = jdbcTemplate.queryForObject(SQL_FIND_CERTIFICATE_BY_ID,
                    new Object[]{id}, mapper);
            return certificate;
        } catch (EmptyResultDataAccessException e) {
            errorCode = RepositoryErrorCode.CERTIFICATE_WITH_SUCH_ID_NOT_EXISTS;
            logger.error(errorCode.getErrorCode() + " : " + errorCode.getErrorMessage(), e);
            throw new RepositoryException(errorCode);
        }
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_CERTIFICATES, mapper);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_CERTIFICATE, id);
    }

    @Override
    public List<Certificate> findCertificatesByTagId(long id) {
        SqlParameterSource params = new MapSqlParameterSource(TAG_ID, id);
        return namedParameterJdbcTemplate.query(SQL_FIND_CERTIFICATES_BY_TAG_ID, params, mapper);
    }

    @Override
    public void updateCertificate(Certificate certificate) {
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE,
                certificate.getCertificateName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreationDate(),
                certificate.getModificationDate(),
                certificate.getDuration(),
                certificate.getId());
    }

    @Override
    public void addTagCertificate(long tagId, long certificateDto) {
        jdbcTemplate.update(ADD_TAG_CERTIFICATE, tagId, certificateDto);
    }

    @Override
    public long create(Certificate certificate) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_CREATE_CERTIFICATE, new String[]{ID});
            ps.setString(1, certificate.getCertificateName());
            ps.setString(2, certificate.getDescription());
            ps.setBigDecimal(3, certificate.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(certificate.getCreationDate().atStartOfDay()));
            ps.setTimestamp(5, Timestamp.valueOf(certificate.getModificationDate().atStartOfDay()));
            ps.setInt(6, certificate.getDuration());
            return ps;
        }, keyHolder);
        return (long) keyHolder.getKey();
    }

    @Override
    public void setTagRepository(ITagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}
