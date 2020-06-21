package com.epam.test;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ICertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class TagRepositoryTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Mock
    private ICertificateRepository certificateRepository;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private TagRowMapper mapper;

    private final List<Tag> expected = new ArrayList<>();

    @Before
    public void setUp() {
    }

    @Test
    public void getAllTagsPositive() {
    }

    @Test
    public void findTagById() {
    }
}