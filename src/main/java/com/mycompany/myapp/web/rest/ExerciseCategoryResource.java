package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExerciseCategory;
import com.mycompany.myapp.service.ExerciseCategoryService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExerciseCategory.
 */
@RestController
@RequestMapping("/api")
public class ExerciseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseCategoryResource.class);
        
    @Inject
    private ExerciseCategoryService exerciseCategoryService;

    /**
     * POST  /exercise-categories : Create a new exerciseCategory.
     *
     * @param exerciseCategory the exerciseCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseCategory, or with status 400 (Bad Request) if the exerciseCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-categories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseCategory> createExerciseCategory(@Valid @RequestBody ExerciseCategory exerciseCategory) throws URISyntaxException {
        log.debug("REST request to save ExerciseCategory : {}", exerciseCategory);
        if (exerciseCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("exerciseCategory", "idexists", "A new exerciseCategory cannot already have an ID")).body(null);
        }
        ExerciseCategory result = exerciseCategoryService.save(exerciseCategory);
        return ResponseEntity.created(new URI("/api/exercise-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("exerciseCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-categories : Updates an existing exerciseCategory.
     *
     * @param exerciseCategory the exerciseCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseCategory,
     * or with status 400 (Bad Request) if the exerciseCategory is not valid,
     * or with status 500 (Internal Server Error) if the exerciseCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-categories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseCategory> updateExerciseCategory(@Valid @RequestBody ExerciseCategory exerciseCategory) throws URISyntaxException {
        log.debug("REST request to update ExerciseCategory : {}", exerciseCategory);
        if (exerciseCategory.getId() == null) {
            return createExerciseCategory(exerciseCategory);
        }
        ExerciseCategory result = exerciseCategoryService.save(exerciseCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("exerciseCategory", exerciseCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-categories : get all the exerciseCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseCategories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/exercise-categories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExerciseCategory>> getAllExerciseCategories(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseCategories");
        Page<ExerciseCategory> page = exerciseCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-categories/:id : get the "id" exerciseCategory.
     *
     * @param id the id of the exerciseCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseCategory, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/exercise-categories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseCategory> getExerciseCategory(@PathVariable String id) {
        log.debug("REST request to get ExerciseCategory : {}", id);
        ExerciseCategory exerciseCategory = exerciseCategoryService.findOne(id);
        return Optional.ofNullable(exerciseCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /exercise-categories/:id : delete the "id" exerciseCategory.
     *
     * @param id the id of the exerciseCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/exercise-categories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExerciseCategory(@PathVariable String id) {
        log.debug("REST request to delete ExerciseCategory : {}", id);
        exerciseCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("exerciseCategory", id.toString())).build();
    }

}
