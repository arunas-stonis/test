package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pass;
import com.mycompany.myapp.service.PassService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pass.
 */
@RestController
@RequestMapping("/api")
public class PassResource {

    private final Logger log = LoggerFactory.getLogger(PassResource.class);
        
    @Inject
    private PassService passService;

    /**
     * POST  /passes : Create a new pass.
     *
     * @param pass the pass to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pass, or with status 400 (Bad Request) if the pass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/passes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pass> createPass(@Valid @RequestBody Pass pass) throws URISyntaxException {
        log.debug("REST request to save Pass : {}", pass);
        if (pass.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pass", "idexists", "A new pass cannot already have an ID")).body(null);
        }
        Pass result = passService.save(pass);
        return ResponseEntity.created(new URI("/api/passes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pass", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passes : Updates an existing pass.
     *
     * @param pass the pass to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pass,
     * or with status 400 (Bad Request) if the pass is not valid,
     * or with status 500 (Internal Server Error) if the pass couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/passes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pass> updatePass(@Valid @RequestBody Pass pass) throws URISyntaxException {
        log.debug("REST request to update Pass : {}", pass);
        if (pass.getId() == null) {
            return createPass(pass);
        }
        Pass result = passService.save(pass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pass", pass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passes : get all the passes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of passes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/passes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pass>> getAllPasses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Passes");
        Page<Pass> page = passService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/passes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /passes/:id : get the "id" pass.
     *
     * @param id the id of the pass to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pass, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/passes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pass> getPass(@PathVariable String id) {
        log.debug("REST request to get Pass : {}", id);
        Pass pass = passService.findOne(id);
        return Optional.ofNullable(pass)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /passes/:id : delete the "id" pass.
     *
     * @param id the id of the pass to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/passes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePass(@PathVariable String id) {
        log.debug("REST request to delete Pass : {}", id);
        passService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pass", id.toString())).build();
    }

}
