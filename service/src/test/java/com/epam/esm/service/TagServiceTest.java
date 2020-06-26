package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.ITagRepository;
import com.epam.esm.service.impl.TagService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    private static int TEST_TAG_ID = 1;

    @Mock
    private ITagRepository tagRepository;
    @Mock
    private DtoMapper dtoMapper;
    @InjectMocks
    private TagService tagService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findTagById() {
        Tag tag = new Tag();
        Mockito.when(tagRepository.findById(TEST_TAG_ID)).thenReturn(tag);
        Mockito.doReturn(new TagDto()).when(dtoMapper).toTagDto(tag);
        TagDto actual = tagService.findById(TEST_TAG_ID);
        TagDto expected = new TagDto();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findAllTags() {
    }

    @Test
    void removeTagById() {
    }

    @Test
    void saveTag() {
    }
}
