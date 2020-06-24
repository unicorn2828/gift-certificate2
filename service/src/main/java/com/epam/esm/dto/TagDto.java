package com.epam.esm.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TagDto extends AbstractDto{
    private Long id;
    private String name;
    private List<CertificateDto> certificates;
}
