package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pass;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Pass entity.
 */
@SuppressWarnings("unused")
public interface PassRepository extends MongoRepository<Pass,String> {

}
