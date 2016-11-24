package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Partner;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Partner entity.
 */
@SuppressWarnings("unused")
public interface PartnerRepository extends MongoRepository<Partner,String> {

}
