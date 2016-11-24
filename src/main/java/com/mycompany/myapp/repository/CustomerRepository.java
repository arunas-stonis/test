package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends MongoRepository<Customer,String> {

}
