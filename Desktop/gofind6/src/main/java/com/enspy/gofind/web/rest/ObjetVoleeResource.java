package com.enspy.gofind.web.rest;

import com.enspy.gofind.domain.ObjetVolee;
import com.enspy.gofind.repository.ObjetVoleeRepository;
import com.enspy.gofind.service.ObjetVoleeService;
import com.enspy.gofind.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.enspy.gofind.domain.ObjetVolee}.
 */
@RestController
@RequestMapping("/api/objet-volees")
public class ObjetVoleeResource {

    private final Logger log = LoggerFactory.getLogger(ObjetVoleeResource.class);

    private static final String ENTITY_NAME = "objetVolee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObjetVoleeService objetVoleeService;

    private final ObjetVoleeRepository objetVoleeRepository;

    public ObjetVoleeResource(ObjetVoleeService objetVoleeService, ObjetVoleeRepository objetVoleeRepository) {
        this.objetVoleeService = objetVoleeService;
        this.objetVoleeRepository = objetVoleeRepository;
    }

    /**
     * {@code POST  /objet-volees} : Create a new objetVolee.
     *
     * @param objetVolee the objetVolee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objetVolee, or with status {@code 400 (Bad Request)} if the objetVolee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ObjetVolee> createObjetVolee(@RequestBody ObjetVolee objetVolee) throws URISyntaxException {
        log.debug("REST request to save ObjetVolee : {}", objetVolee);
        if (objetVolee.getId() != null) {
            throw new BadRequestAlertException("A new objetVolee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        objetVolee = objetVoleeService.save(objetVolee);
        return ResponseEntity.created(new URI("/api/objet-volees/" + objetVolee.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, objetVolee.getId().toString()))
            .body(objetVolee);
    }

    /**
     * {@code PUT  /objet-volees/:id} : Updates an existing objetVolee.
     *
     * @param id the id of the objetVolee to save.
     * @param objetVolee the objetVolee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objetVolee,
     * or with status {@code 400 (Bad Request)} if the objetVolee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objetVolee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObjetVolee> updateObjetVolee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObjetVolee objetVolee
    ) throws URISyntaxException {
        log.debug("REST request to update ObjetVolee : {}, {}", id, objetVolee);
        if (objetVolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objetVolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objetVoleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        objetVolee = objetVoleeService.update(objetVolee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objetVolee.getId().toString()))
            .body(objetVolee);
    }

    /**
     * {@code PATCH  /objet-volees/:id} : Partial updates given fields of an existing objetVolee, field will ignore if it is null
     *
     * @param id the id of the objetVolee to save.
     * @param objetVolee the objetVolee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objetVolee,
     * or with status {@code 400 (Bad Request)} if the objetVolee is not valid,
     * or with status {@code 404 (Not Found)} if the objetVolee is not found,
     * or with status {@code 500 (Internal Server Error)} if the objetVolee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ObjetVolee> partialUpdateObjetVolee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ObjetVolee objetVolee
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObjetVolee partially : {}, {}", id, objetVolee);
        if (objetVolee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objetVolee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objetVoleeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObjetVolee> result = objetVoleeService.partialUpdate(objetVolee);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objetVolee.getId().toString())
        );
    }

    /**
     * {@code GET  /objet-volees} : get all the objetVolees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objetVolees in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ObjetVolee>> getAllObjetVolees(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ObjetVolees");
        Page<ObjetVolee> page = objetVoleeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /objet-volees/:id} : get the "id" objetVolee.
     *
     * @param id the id of the objetVolee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objetVolee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObjetVolee> getObjetVolee(@PathVariable("id") Long id) {
        log.debug("REST request to get ObjetVolee : {}", id);
        Optional<ObjetVolee> objetVolee = objetVoleeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objetVolee);
    }

    /**
     * {@code DELETE  /objet-volees/:id} : delete the "id" objetVolee.
     *
     * @param id the id of the objetVolee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjetVolee(@PathVariable("id") Long id) {
        log.debug("REST request to delete ObjetVolee : {}", id);
        objetVoleeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
