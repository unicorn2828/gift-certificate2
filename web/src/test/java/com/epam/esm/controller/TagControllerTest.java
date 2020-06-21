package com.epam.esm.controller;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.impl.TagRepository;
import org.junit.gen5.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class TagControllerTest {

    @InjectMocks
    private TagRepository tagRepository;
    private final List<Tag> expected = new ArrayList<>();


    @org.junit.gen5.api.Test
    public void getAllTagsPositive() {
        System.out.println(tagRepository);
        List<Tag> actual = tagRepository.findAllTags();

        assertNotNull(actual.size());
    }

    @Test
    public void findTagById() {
    }
}