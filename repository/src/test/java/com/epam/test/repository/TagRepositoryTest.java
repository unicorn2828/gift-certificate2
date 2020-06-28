package com.epam.test.repository;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ITagRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class TagRepositoryTest {
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "test";
    private Tag expected;
    @Autowired
    private ITagRepository tagRepository;

    @Before
    public void setup() {
        expected = new Tag();
        expected.setId(TEST_ID);
        expected.setName(TEST_NAME);
    }

    @After
    public void tierDown() {
        expected = null;
    }

    @Test
    public void findById() {
        Tag actual = tagRepository.findById(TEST_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void findByName() {
        List<Tag> tagList = tagRepository.findByName(TEST_NAME);
        boolean actual = tagList.contains(expected);
        assertTrue(actual);

    }

    @Test
    public void isTagExist() {
        boolean actual = tagRepository.isTagExist(TEST_NAME);
        assertTrue(actual);
    }

    @Test
    public void findAll() {
        boolean actual = tagRepository.findAll().isEmpty();
        assertFalse(actual);
    }

    @Test
    public void findTagsByCertificateId() {
        boolean actual = tagRepository.findTagsByCertificateId(TEST_ID).isEmpty();
        assertFalse(actual);
    }
}
