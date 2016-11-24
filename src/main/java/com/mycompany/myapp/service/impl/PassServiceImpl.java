package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PassService;
import com.mycompany.myapp.domain.Pass;
import com.mycompany.myapp.repository.PassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Pass.
 */
@Service
public class PassServiceImpl implements PassService{

    private final Logger log = LoggerFactory.getLogger(PassServiceImpl.class);
    
    @Inject
    private PassRepository passRepository;

    /**
     * Save a pass.
     *
     * @param pass the entity to save
     * @return the persisted entity
     */
    public Pass save(Pass pass) {
        log.debug("Request to save Pass : {}", pass);
        Pass result = passRepository.save(pass);
        return result;
    }

    /**
     *  Get all the passes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Pass> findAll(Pageable pageable) {
        log.debug("Request to get all Passes");
        Page<Pass> result = passRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pass by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Pass findOne(String id) {
        log.debug("Request to get Pass : {}", id);
        Pass pass = passRepository.findOne(id);
        return pass;
    }

    /**
     *  Delete the  pass by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Pass : {}", id);
        passRepository.delete(id);
    }
}
