package com.epam.test.repository;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.model.Certificate;
import com.epam.esm.repository.ICertificateRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class CertificateRepositoryTest {
    private static final Long TEST_ID = 1L;
    private static final String TEST_NAME = "test";
    private static final BigDecimal TEST_PRICE = new BigDecimal(10);
    private static final LocalDate TEST_CREATION_DATE = LocalDate.of(2020, 01, 01);
    private static final LocalDate TEST_MODIFICATION_DATE = LocalDate.of(2020, 01, 01);
    private static final int TEST_DURATION = 10;
    private Certificate expected;
    @Autowired
    private ICertificateRepository certificateRepository;

    @Before
    public void setup() {
        expected = new Certificate();
        expected.setId(TEST_ID);
        expected.setCertificateName(TEST_NAME);
        expected.setDescription(TEST_NAME);
        expected.setPrice(TEST_PRICE);
        expected.setCreationDate(TEST_CREATION_DATE);
        expected.setModificationDate(TEST_MODIFICATION_DATE);
        expected.setDuration(TEST_DURATION);
    }

    @After
    public void tierDown() {
        expected = null;
    }

    @Test
    public void findById() {
        Certificate actual = certificateRepository.findById(TEST_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void findByName() {
        List<Certificate> tagList = certificateRepository.findByName(TEST_NAME);
        boolean actual = tagList.contains(expected);
        assertTrue(actual);

    }

    @Test
    public void findAll() {
        boolean actual = certificateRepository.findAll().isEmpty();
        assertFalse(actual);
    }

    @Test
    public void findTagsByCertificateId() {
        boolean actual = certificateRepository.findCertificatesByTagId(TEST_ID).isEmpty();
        assertFalse(actual);
    }
}
