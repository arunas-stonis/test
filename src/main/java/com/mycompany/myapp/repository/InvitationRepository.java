package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Invitation;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Invitation entity.
 */
@SuppressWarnings("unused")
public interface InvitationRepository extends MongoRepository<Invitation,String> {

}
