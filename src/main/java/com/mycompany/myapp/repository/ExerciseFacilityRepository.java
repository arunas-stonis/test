package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExerciseFacility;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ExerciseFacility entity.
 */
@SuppressWarnings("unused")
public interface ExerciseFacilityRepository extends MongoRepository<ExerciseFacility,String> {

}
