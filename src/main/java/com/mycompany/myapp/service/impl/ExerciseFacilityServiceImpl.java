package com.mycompany.myapp.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.mycompany.myapp.domain.ExerciseFacility;
import com.mycompany.myapp.repository.ExerciseFacilityRepository;
import com.mycompany.myapp.service.ExerciseFacilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.*;
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

    public Double[] findCoordinates(String address) {
        Double result[] = new Double[2];
        try {
            GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCu9Vcr3JC1T5wb4RcvsvNg-VuS49GqB50");
            GeocodingResult[] query = GeocodingApi.geocode(context,
                address).await();
            result[0] = query[0].geometry.location.lng;
            result[1] = query[0].geometry.location.lat;
        } catch (Exception e) {
            log.info("Error while searching for coordinates at Google", e);
        }
        return result;
    }

    public List<ExerciseFacility> findFacilitiesByLocation(String location) {
        log.info("Request to get all ExerciseFacilities");
        Double[] currentLocation = findCoordinates(location);
        log.info("Cordinates detected x={} y={}", currentLocation[0], currentLocation[1]);
        Point p = new Point(currentLocation[0], currentLocation[1]);
        Distance d = new Distance(100000000, Metrics.KILOMETERS);
        Circle area = new Circle(p, d);
        return exerciseFacilityRepository.findByCoordinatesWithin(area);
    }

    private static final int a = 100;

    public List<ExerciseFacility> findFacilitiesByLocation(String location, String type) {
        log.info("Request to get all ExerciseFacilities");
        Double[] currentLocation = findCoordinates(location);
        log.info("Cordinates detected x={} y={}", currentLocation[0], currentLocation[1]);
        Point p = new Point(currentLocation[0], currentLocation[1]);
        Distance d = new Distance(a, Metrics.KILOMETERS);
        Circle c = new Circle(p, d);
        Box b = new Box(new Point(p.getX()-a/2, p.getY()-a/2), new Point(p.getX()+a/2, p.getY()+a/2) );
        switch (type) {
            case "circle": return exerciseFacilityRepository.findByCoordinatesWithin(c);
            case "box": return exerciseFacilityRepository.findByCoordinatesWithin(b);
            case "point": default: return exerciseFacilityRepository.findByCoordinatesWithin(p, d);
        }
    }

}
