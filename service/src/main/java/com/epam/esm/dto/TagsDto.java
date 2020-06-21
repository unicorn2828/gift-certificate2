package com.epam.esm.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TagsDto extends AbstractDto{
    private List<TagDto> tags;
}
