package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ExerciseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ExerciseCategory.
 */
public interface ExerciseCategoryService {

    /**
     * Save a exerciseCategory.
     *
     * @param exerciseCategory the entity to save
     * @return the persisted entity
     */
    ExerciseCategory save(ExerciseCategory exerciseCategory);

    /**
     *  Get all the exerciseCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExerciseCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" exerciseCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExerciseCategory findOne(String id);

    /**
     *  Delete the "id" exerciseCategory.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
