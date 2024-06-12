package org.enspy.gofind.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.enspy.gofind.domain.Trajet;
import org.enspy.gofind.repository.TrajetRepository;
import org.enspy.gofind.service.TrajetService;
import org.enspy.gofind.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.enspy.gofind.domain.Trajet}.
 */
@RestController
@RequestMapping("/api/trajets")
public class TrajetResource {

    private final Logger log = LoggerFactory.getLogger(TrajetResource.class);

    private static final String ENTITY_NAME = "trajet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajetService trajetService;

    private final TrajetRepository trajetRepository;

    public TrajetResource(TrajetService trajetService, TrajetRepository trajetRepository) {
        this.trajetService = trajetService;
        this.trajetRepository = trajetRepository;
    }

    /**
     * {@code POST  /trajets} : Create a new trajet.
     *
     * @param trajet the trajet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trajet, or with status {@code 400 (Bad Request)} if the trajet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Trajet> createTrajet(@RequestBody Trajet trajet) throws URISyntaxException {
        log.debug("REST request to save Trajet : {}", trajet);
        if (trajet.getId() != null) {
            throw new BadRequestAlertException("A new trajet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trajet = trajetService.save(trajet);
        return ResponseEntity.created(new URI("/api/trajets/" + trajet.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, trajet.getId().toString()))
            .body(trajet);
    }

    /**
     * {@code PUT  /trajets/:id} : Updates an existing trajet.
     *
     * @param id the id of the trajet to save.
     * @param trajet the trajet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajet,
     * or with status {@code 400 (Bad Request)} if the trajet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trajet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Trajet> updateTrajet(@PathVariable(value = "id", required = false) final Long id, @RequestBody Trajet trajet)
        throws URISyntaxException {
        log.debug("REST request to update Trajet : {}, {}", id, trajet);
        if (trajet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trajet = trajetService.update(trajet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajet.getId().toString()))
            .body(trajet);
    }

    /**
     * {@code PATCH  /trajets/:id} : Partial updates given fields of an existing trajet, field will ignore if it is null
     *
     * @param id the id of the trajet to save.
     * @param trajet the trajet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajet,
     * or with status {@code 400 (Bad Request)} if the trajet is not valid,
     * or with status {@code 404 (Not Found)} if the trajet is not found,
     * or with status {@code 500 (Internal Server Error)} if the trajet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Trajet> partialUpdateTrajet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Trajet trajet
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trajet partially : {}, {}", id, trajet);
        if (trajet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trajet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trajetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Trajet> result = trajetService.partialUpdate(trajet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trajet.getId().toString())
        );
    }

    /**
     * {@code GET  /trajets} : get all the trajets.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajets in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Trajet>> getAllTrajets(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("annonce-is-null".equals(filter)) {
            log.debug("REST request to get all Trajets where annonce is null");
            return new ResponseEntity<>(trajetService.findAllWhereAnnonceIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Trajets");
        Page<Trajet> page = trajetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trajets/:id} : get the "id" trajet.
     *
     * @param id the id of the trajet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trajet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trajet> getTrajet(@PathVariable("id") Long id) {
        log.debug("REST request to get Trajet : {}", id);
        Optional<Trajet> trajet = trajetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trajet);
    }

    /**
     * {@code DELETE  /trajets/:id} : delete the "id" trajet.
     *
     * @param id the id of the trajet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable("id") Long id) {
        log.debug("REST request to delete Trajet : {}", id);
        trajetService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
