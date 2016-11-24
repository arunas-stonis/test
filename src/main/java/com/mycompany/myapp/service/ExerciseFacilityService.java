package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ExerciseFacility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing ExerciseFacility.
 */
public interface ExerciseFacilityService {

    /**
     * Save a exerciseFacility.
     *
     * @param exerciseFacility the entity to save
     * @return the persisted entity
     */
    ExerciseFacility save(ExerciseFacility exerciseFacility);

    /**
     *  Get all the exerciseFacilities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExerciseFacility> findAll(Pageable pageable);

    /**
     *  Get the "id" exerciseFacility.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExerciseFacility findOne(String id);

    /**
     *  Delete the "id" exerciseFacility.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
