package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ExerciseCategoryService;
import com.mycompany.myapp.domain.ExerciseCategory;
import com.mycompany.myapp.repository.ExerciseCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ExerciseCategory.
 */
@Service
public class ExerciseCategoryServiceImpl implements ExerciseCategoryService{

    private final Logger log = LoggerFactory.getLogger(ExerciseCategoryServiceImpl.class);
    
    @Inject
    private ExerciseCategoryRepository exerciseCategoryRepository;

    /**
     * Save a exerciseCategory.
     *
     * @param exerciseCategory the entity to save
     * @return the persisted entity
     */
    public ExerciseCategory save(ExerciseCategory exerciseCategory) {
        log.debug("Request to save ExerciseCategory : {}", exerciseCategory);
        ExerciseCategory result = exerciseCategoryRepository.save(exerciseCategory);
        return result;
    }

    /**
     *  Get all the exerciseCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ExerciseCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseCategories");
        Page<ExerciseCategory> result = exerciseCategoryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one exerciseCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ExerciseCategory findOne(String id) {
        log.debug("Request to get ExerciseCategory : {}", id);
        ExerciseCategory exerciseCategory = exerciseCategoryRepository.findOne(id);
        return exerciseCategory;
    }

    /**
     *  Delete the  exerciseCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ExerciseCategory : {}", id);
        exerciseCategoryRepository.delete(id);
    }
}
