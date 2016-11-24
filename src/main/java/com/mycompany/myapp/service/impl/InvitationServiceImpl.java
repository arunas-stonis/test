package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.InvitationService;
import com.mycompany.myapp.domain.Invitation;
import com.mycompany.myapp.repository.InvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Invitation.
 */
@Service
public class InvitationServiceImpl implements InvitationService{

    private final Logger log = LoggerFactory.getLogger(InvitationServiceImpl.class);
    
    @Inject
    private InvitationRepository invitationRepository;

    /**
     * Save a invitation.
     *
     * @param invitation the entity to save
     * @return the persisted entity
     */
    public Invitation save(Invitation invitation) {
        log.debug("Request to save Invitation : {}", invitation);
        Invitation result = invitationRepository.save(invitation);
        return result;
    }

    /**
     *  Get all the invitations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Invitation> findAll(Pageable pageable) {
        log.debug("Request to get all Invitations");
        Page<Invitation> result = invitationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one invitation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Invitation findOne(String id) {
        log.debug("Request to get Invitation : {}", id);
        Invitation invitation = invitationRepository.findOne(id);
        return invitation;
    }

    /**
     *  Delete the  invitation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Invitation : {}", id);
        invitationRepository.delete(id);
    }
}
