package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ExerciseFacilityService;
import com.mycompany.myapp.domain.ExerciseFacility;
import com.mycompany.myapp.repository.ExerciseFacilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ExerciseFacility.
 */
@Service
public class ExerciseFacilityServiceImpl implements ExerciseFacilityService{

    private final Logger log = LoggerFactory.getLogger(ExerciseFacilityServiceImpl.class);
    
    @Inject
    private ExerciseFacilityRepository exerciseFacilityRepository;

    /**
     * Save a exerciseFacility.
     *
     * @param exerciseFacility the entity to save
     * @return the persisted entity
     */
    public ExerciseFacility save(ExerciseFacility exerciseFacility) {
        log.debug("Request to save ExerciseFacility : {}", exerciseFacility);
        ExerciseFacility result = exerciseFacilityRepository.save(exerciseFacility);
        return result;
    }

    /**
     *  Get all the exerciseFacilities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ExerciseFacility> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseFacilities");
        Page<ExerciseFacility> result = exerciseFacilityRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one exerciseFacility by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ExerciseFacility findOne(String id) {
        log.debug("Request to get ExerciseFacility : {}", id);
        ExerciseFacility exerciseFacility = exerciseFacilityRepository.findOne(id);
        return exerciseFacility;
    }

    /**
     *  Delete the  exerciseFacility by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ExerciseFacility : {}", id);
        exerciseFacilityRepository.delete(id);
    }
}
