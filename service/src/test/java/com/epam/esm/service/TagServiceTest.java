package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.impl.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class TagServiceTest {
    private static int TEST_TAG_ID = 1;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DtoMapper dtoMapper;
    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findTagById() {
        //Mockito.doReturn(new TagDto()).when(dtoMapper).toTagDto(new Tag());
        Mockito.when(tagRepository.findById(Mockito.anyLong())).thenReturn(Mockito.any(Tag.class));
        Mockito.when(dtoMapper.toTagDto(Mockito.any(Tag.class))).thenReturn(Mockito.any(TagDto.class));

        TagDto actual = tagService.findById(TEST_TAG_ID);
        TagDto expected = new TagDto();
        Assertions.assertEquals(expected, actual);
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
