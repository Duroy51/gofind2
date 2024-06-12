package org.enspy.gofind.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.enspy.gofind.domain.DeclarationVol;
import org.enspy.gofind.repository.DeclarationVolRepository;
import org.enspy.gofind.service.DeclarationVolService;
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
 * REST controller for managing {@link org.enspy.gofind.domain.DeclarationVol}.
 */
@RestController
@RequestMapping("/api/declaration-vols")
public class DeclarationVolResource {

    private final Logger log = LoggerFactory.getLogger(DeclarationVolResource.class);

    private static final String ENTITY_NAME = "declarationVol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeclarationVolService declarationVolService;

    private final DeclarationVolRepository declarationVolRepository;

    public DeclarationVolResource(DeclarationVolService declarationVolService, DeclarationVolRepository declarationVolRepository) {
        this.declarationVolService = declarationVolService;
        this.declarationVolRepository = declarationVolRepository;
    }

    /**
     * {@code POST  /declaration-vols} : Create a new declarationVol.
     *
     * @param declarationVol the declarationVol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new declarationVol, or with status {@code 400 (Bad Request)} if the declarationVol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DeclarationVol> createDeclarationVol(@RequestBody DeclarationVol declarationVol) throws URISyntaxException {
        log.debug("REST request to save DeclarationVol : {}", declarationVol);
        if (declarationVol.getId() != null) {
            throw new BadRequestAlertException("A new declarationVol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        declarationVol = declarationVolService.save(declarationVol);
        return ResponseEntity.created(new URI("/api/declaration-vols/" + declarationVol.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, declarationVol.getId().toString()))
            .body(declarationVol);
    }

    /**
     * {@code PUT  /declaration-vols/:id} : Updates an existing declarationVol.
     *
     * @param id the id of the declarationVol to save.
     * @param declarationVol the declarationVol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declarationVol,
     * or with status {@code 400 (Bad Request)} if the declarationVol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the declarationVol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeclarationVol> updateDeclarationVol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeclarationVol declarationVol
    ) throws URISyntaxException {
        log.debug("REST request to update DeclarationVol : {}, {}", id, declarationVol);
        if (declarationVol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declarationVol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declarationVolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        declarationVol = declarationVolService.update(declarationVol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declarationVol.getId().toString()))
            .body(declarationVol);
    }

    /**
     * {@code PATCH  /declaration-vols/:id} : Partial updates given fields of an existing declarationVol, field will ignore if it is null
     *
     * @param id the id of the declarationVol to save.
     * @param declarationVol the declarationVol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated declarationVol,
     * or with status {@code 400 (Bad Request)} if the declarationVol is not valid,
     * or with status {@code 404 (Not Found)} if the declarationVol is not found,
     * or with status {@code 500 (Internal Server Error)} if the declarationVol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeclarationVol> partialUpdateDeclarationVol(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeclarationVol declarationVol
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeclarationVol partially : {}, {}", id, declarationVol);
        if (declarationVol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, declarationVol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!declarationVolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeclarationVol> result = declarationVolService.partialUpdate(declarationVol);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, declarationVol.getId().toString())
        );
    }

    /**
     * {@code GET  /declaration-vols} : get all the declarationVols.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of declarationVols in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DeclarationVol>> getAllDeclarationVols(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("annonce-is-null".equals(filter)) {
            log.debug("REST request to get all DeclarationVols where annonce is null");
            return new ResponseEntity<>(declarationVolService.findAllWhereAnnonceIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of DeclarationVols");
        Page<DeclarationVol> page = declarationVolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /declaration-vols/:id} : get the "id" declarationVol.
     *
     * @param id the id of the declarationVol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the declarationVol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeclarationVol> getDeclarationVol(@PathVariable("id") Long id) {
        log.debug("REST request to get DeclarationVol : {}", id);
        Optional<DeclarationVol> declarationVol = declarationVolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(declarationVol);
    }

    /**
     * {@code DELETE  /declaration-vols/:id} : delete the "id" declarationVol.
     *
     * @param id the id of the declarationVol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeclarationVol(@PathVariable("id") Long id) {
        log.debug("REST request to delete DeclarationVol : {}", id);
        declarationVolService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
