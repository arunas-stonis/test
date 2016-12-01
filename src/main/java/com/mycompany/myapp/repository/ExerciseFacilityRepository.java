package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExerciseFacility;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the ExerciseFacility entity.
 */
@SuppressWarnings("unused")
public interface ExerciseFacilityRepository extends MongoRepository<ExerciseFacility,String> {

    List<ExerciseFacility> findByCoordinatesWithin(Circle c);

    List<ExerciseFacility> findByCoordinatesWithin(Box c);

    List<ExerciseFacility> findByCoordinatesWithin(Point p, Distance d);
}
