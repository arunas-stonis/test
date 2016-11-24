package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Partner.
 */
public interface PartnerService {

    /**
     * Save a partner.
     *
     * @param partner the entity to save
     * @return the persisted entity
     */
    Partner save(Partner partner);

    /**
     *  Get all the partners.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Partner> findAll(Pageable pageable);

    /**
     *  Get the "id" partner.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Partner findOne(String id);

    /**
     *  Delete the "id" partner.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
