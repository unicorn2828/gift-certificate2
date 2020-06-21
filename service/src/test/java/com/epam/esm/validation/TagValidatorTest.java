package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.RepositoryException;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.gen5.api.Assertions.assertTrue;

class TagValidatorTest {
    private static final String TEST = "test";

    @Test
    void isTag() {
        TagDto test = new TagDto();
        test.setName(TEST);
        boolean actual = TagValidator.isTag(test);
        assertTrue(actual);
    }

    @Test
    void isTagNegative() {
        TagDto actual = new TagDto();
        actual.setName(null);
        Assertions.assertThrows(RepositoryException.class, () -> {
            TagValidator.isTag(actual);
        });
    }
    @Test
    void isTagNegativeNull() {
               Assertions.assertThrows(RepositoryException.class, () -> {
            TagValidator.isTag(null);
        });
    }
}
