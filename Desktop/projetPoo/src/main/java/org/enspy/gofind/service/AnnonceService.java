package org.enspy.gofind.service;

import java.util.Optional;
import org.enspy.gofind.domain.Annonce;
import org.enspy.gofind.repository.AnnonceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.enspy.gofind.domain.Annonce}.
 */
@Service
@Transactional
public class AnnonceService {

    private final Logger log = LoggerFactory.getLogger(AnnonceService.class);

    private final AnnonceRepository annonceRepository;

    public AnnonceService(AnnonceRepository annonceRepository) {
        this.annonceRepository = annonceRepository;
    }

    /**
     * Save a annonce.
     *
     * @param annonce the entity to save.
     * @return the persisted entity.
     */
    public Annonce save(Annonce annonce) {
        log.debug("Request to save Annonce : {}", annonce);
        return annonceRepository.save(annonce);
    }

    /**
     * Update a annonce.
     *
     * @param annonce the entity to save.
     * @return the persisted entity.
     */
    public Annonce update(Annonce annonce) {
        log.debug("Request to update Annonce : {}", annonce);
        return annonceRepository.save(annonce);
    }

    /**
     * Partially update a annonce.
     *
     * @param annonce the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Annonce> partialUpdate(Annonce annonce) {
        log.debug("Request to partially update Annonce : {}", annonce);

        return annonceRepository
            .findById(annonce.getId())
            .map(existingAnnonce -> {
                if (annonce.getTitre() != null) {
                    existingAnnonce.setTitre(annonce.getTitre());
                }
                if (annonce.getDescription() != null) {
                    existingAnnonce.setDescription(annonce.getDescription());
                }
                if (annonce.getType() != null) {
                    existingAnnonce.setType(annonce.getType());
                }
                if (annonce.getStatut() != null) {
                    existingAnnonce.setStatut(annonce.getStatut());
                }
                if (annonce.getDatecreation() != null) {
                    existingAnnonce.setDatecreation(annonce.getDatecreation());
                }

                return existingAnnonce;
            })
            .map(annonceRepository::save);
    }

    /**
     * Get all the annonces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Annonce> findAll(Pageable pageable) {
        log.debug("Request to get all Annonces");
        return annonceRepository.findAll(pageable);
    }

    /**
     * Get one annonce by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Annonce> findOne(Long id) {
        log.debug("Request to get Annonce : {}", id);
        return annonceRepository.findById(id);
    }

    /**
     * Delete the annonce by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Annonce : {}", id);
        annonceRepository.deleteById(id);
    }
}
