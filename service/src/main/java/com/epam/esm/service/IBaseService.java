package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;

import java.util.Map;

public interface IBaseService<T extends AbstractDto, T1 extends AbstractDto>{

    /**
     * This method creates a new entity
     *
     * @param dto - the new dto to save
     * @return {link} new abstractDto
     * */
    T create(final T dto);

    /**
     * This method finds a entity by id
     *
     * @param id - id of abstractDto
     * @return {link} abstractDto
     */
    T findById(final long id);

    /**
     * This method removes by id
     *
     * @param id - id
     * @param id - id
     * @return {link void}
     */
    void removeById(final long id);

    /**
     * This method finds all entities
     *
     * @return {link} list of abstractDto
     */
    T1 findAll(Map<String, String> allParams);
}
