package com.epam.esm.model;

import lombok.Data;

import java.util.List;

@Data
public class Tag extends BaseModel {
    private long id;
    private String name;
    private List<Certificate> certificates;
}
