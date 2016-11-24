package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PartnerService;
import com.mycompany.myapp.domain.Partner;
import com.mycompany.myapp.repository.PartnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Partner.
 */
@Service
public class PartnerServiceImpl implements PartnerService{

    private final Logger log = LoggerFactory.getLogger(PartnerServiceImpl.class);
    
    @Inject
    private PartnerRepository partnerRepository;

    /**
     * Save a partner.
     *
     * @param partner the entity to save
     * @return the persisted entity
     */
    public Partner save(Partner partner) {
        log.debug("Request to save Partner : {}", partner);
        Partner result = partnerRepository.save(partner);
        return result;
    }

    /**
     *  Get all the partners.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Partner> findAll(Pageable pageable) {
        log.debug("Request to get all Partners");
        Page<Partner> result = partnerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one partner by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Partner findOne(String id) {
        log.debug("Request to get Partner : {}", id);
        Partner partner = partnerRepository.findOne(id);
        return partner;
    }

    /**
     *  Delete the  partner by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Partner : {}", id);
        partnerRepository.delete(id);
    }
}
