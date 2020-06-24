package com.epam.esm.repository;

import java.util.List;

public interface IBaseRepository<T> {
    /**
     * This method save new model to db
     *
     * @param model - the new model to save
     * @return {link} id of model
     */
    long create(T model);

    /**
     * This method removes a model by id
     *
     * @param id - id of model
     * @return {link void}
     */
    void delete(long id);

    /**
     * This method finds a model in db by id
     *
     * @param id - id of model
     * @return {link} model
     */
    T findById(long id);

    /**
     * This method finds all models in database
     *
     * @return {link} List of models
     */
    List<T> findAll();
}
