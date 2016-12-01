package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExerciseFacility;
import com.mycompany.myapp.service.ExerciseFacilityService;
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
 * REST controller for managing ExerciseFacility.
 */
@RestController
@RequestMapping("/api")
public class ExerciseFacilityResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseFacilityResource.class);

    @Inject
    private ExerciseFacilityService exerciseFacilityService;

    /**
     * POST  /exercise-facilities : Create a new exerciseFacility.
     *
     * @param exerciseFacility the exerciseFacility to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseFacility, or with status 400 (Bad Request) if the exerciseFacility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-facilities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseFacility> createExerciseFacility(@Valid @RequestBody ExerciseFacility exerciseFacility) throws URISyntaxException {
        log.debug("REST request to save ExerciseFacility : {}", exerciseFacility);
        if (exerciseFacility.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("exerciseFacility", "idexists", "A new exerciseFacility cannot already have an ID")).body(null);
        }
        ExerciseFacility result = exerciseFacilityService.save(exerciseFacility);
        return ResponseEntity.created(new URI("/api/exercise-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("exerciseFacility", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-facilities : Updates an existing exerciseFacility.
     *
     * @param exerciseFacility the exerciseFacility to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseFacility,
     * or with status 400 (Bad Request) if the exerciseFacility is not valid,
     * or with status 500 (Internal Server Error) if the exerciseFacility couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-facilities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseFacility> updateExerciseFacility(@Valid @RequestBody ExerciseFacility exerciseFacility) throws URISyntaxException {
        log.debug("REST request to update ExerciseFacility : {}", exerciseFacility);
        if (exerciseFacility.getId() == null) {
            return createExerciseFacility(exerciseFacility);
        }
        ExerciseFacility result = exerciseFacilityService.save(exerciseFacility);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("exerciseFacility", exerciseFacility.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-facilities : get all the exerciseFacilities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseFacilities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/exercise-facilities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExerciseFacility>> getAllExerciseFacilities(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseFacilities");
        Page<ExerciseFacility> page = exerciseFacilityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-facilities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-facilities/:id : get the "id" exerciseFacility.
     *
     * @param id the id of the exerciseFacility to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseFacility, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/exercise-facilities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseFacility> getExerciseFacility(@PathVariable String id) {
        log.debug("REST request to get ExerciseFacility : {}", id);
        ExerciseFacility exerciseFacility = exerciseFacilityService.findOne(id);
        return Optional.ofNullable(exerciseFacility)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /exercise-facilities/:id : delete the "id" exerciseFacility.
     *
     * @param id the id of the exerciseFacility to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/exercise-facilities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExerciseFacility(@PathVariable String id) {
        log.debug("REST request to delete ExerciseFacility : {}", id);
        exerciseFacilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("exerciseFacility", id.toString())).build();
    }

    @RequestMapping(value = "/location-by-adress/{address}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Double[] findCoordinates(@PathVariable String address) {
        log.debug("REST request to get findCoordinates : {}", address);
        return exerciseFacilityService.findCoordinates(address);

    }

    @RequestMapping(value = "/filter-by-location",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExerciseFacility>> filterByLocation(Pageable pageable, @RequestParam("term") String location, @RequestParam("type") String type) throws URISyntaxException {
        log.debug("REST request to get facilities by location : {} type {}", location, type);
        return new ResponseEntity<>(exerciseFacilityService.findFacilitiesByLocation(location, type), HttpStatus.OK);
//        Page<ExerciseFacility> page = exerciseFacilityService.findFacilitiesByLocation(pageable, location);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filter-by-location");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
