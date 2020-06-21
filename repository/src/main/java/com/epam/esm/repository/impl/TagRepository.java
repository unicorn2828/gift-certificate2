package com.epam.esm.repository.impl;

import com.epam.esm.exception.RepositoryErrorCode;
import com.epam.esm.exception.RepositoryException;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.util.List;

import static com.epam.esm.repository.data.TagRepositoryData.*;

@Repository("tagRepository")
@RequiredArgsConstructor
public class TagRepository implements ITagRepository {
    static final Logger logger = LogManager.getLogger();
    private RepositoryErrorCode errorCode = null;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ICertificateRepository certificateRepository;
    private final JdbcTemplate jdbcTemplate;
    private final TagRowMapper mapper;

    @PostConstruct
    public void init() {
        certificateRepository.setTagRepository(this);
    }

    @Override
    public Tag findById(long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_TAG_BY_ID, new Object[]{id}, mapper);
        } catch (EmptyResultDataAccessException e) {
            errorCode = RepositoryErrorCode.TAG_WITH_SUCH_ID_NOT_EXIST;
            logger.error(errorCode.getErrorCode() + " : " + errorCode.getErrorMessage(), e);
            throw new RepositoryException(errorCode);
        }
    }

    public List<Tag> findByPartName(String partName) {
        try {
            SqlParameterSource params = new MapSqlParameterSource(TAG_NAME, partName);
            return namedParameterJdbcTemplate.query(SQL_FUNCTION, params, mapper);
        } catch (EmptyResultDataAccessException e) {
            errorCode = RepositoryErrorCode.TAG_WITH_SUCH_NAME_NOT_EXISTS;
            logger.error(errorCode.getErrorCode() + " : " + errorCode.getErrorMessage(), e);
            throw new RepositoryException(errorCode);
        }
    }

    @Override
    public Tag findTagByName(String tagName) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_TAG_BY_NAME, new Object[]{tagName}, mapper);
        } catch (EmptyResultDataAccessException e) {
            errorCode = RepositoryErrorCode.TAG_WITH_SUCH_NAME_NOT_EXISTS;
            logger.error(errorCode.getErrorCode() + " : " + errorCode.getErrorMessage(), e);
            throw new RepositoryException(errorCode);
        }
    }

    @Override
    public boolean isTagExist(String tagName) {
        return jdbcTemplate.queryForObject(SQL_CHECK_ROW_EXISTS, new Object[]{tagName}, Integer.class) > 0;
    }

    @Override
    public List<Tag> findAllTags() {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, mapper);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG, id);
    }

    @Override
    public void deleteTagFromTagCertificate(long id) {
        jdbcTemplate.update(SQL_DELETE_TAG_FROM_TAG_CERTIFICATE, id);
    }

    @Override
    public long create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_CREATE_TAG, new String[]{ID});
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        return (long) keyHolder.getKey();
    }

    @Override
    public List<Tag> findTagsByCertificateId(long id) {
        SqlParameterSource params = new MapSqlParameterSource(CERTIFICATE_ID, id);
        return namedParameterJdbcTemplate.query(SQL_FIND_TAGS_BY_CERTIFICATE_ID, params, mapper);
    }
}
