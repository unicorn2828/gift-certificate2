package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;
import com.epam.esm.service.impl.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TagServiceTest {
    private TagService tagService= Mockito.mock(TagService.class);
    private static int TEST_CERTIFICATE_ID = 1;

    @Test
    void findTagById() {
        Mockito.when(tagService.findById(TEST_CERTIFICATE_ID)).thenReturn(new TagDto());
        TagDto actual = tagService.findById(TEST_CERTIFICATE_ID);
        TagDto expected = new TagDto();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findAllTags() {
        Mockito.when(tagService.findAll()).thenReturn(new TagsDto());
        TagsDto actual = tagService.findAll();
        TagsDto expected = new TagsDto();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void removeTagById() {
    }

    @Test
    void saveTag() {
    }
}
