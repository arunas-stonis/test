package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExerciseCategory;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ExerciseCategory entity.
 */
@SuppressWarnings("unused")
public interface ExerciseCategoryRepository extends MongoRepository<ExerciseCategory,String> {

}
