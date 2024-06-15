package com.enspy.gofind.service;

import com.enspy.gofind.domain.ObjetVolee;
import com.enspy.gofind.repository.ObjetVoleeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.enspy.gofind.domain.ObjetVolee}.
 */
@Service
@Transactional
public class ObjetVoleeService {

    private final Logger log = LoggerFactory.getLogger(ObjetVoleeService.class);

    private final ObjetVoleeRepository objetVoleeRepository;

    public ObjetVoleeService(ObjetVoleeRepository objetVoleeRepository) {
        this.objetVoleeRepository = objetVoleeRepository;
    }

    /**
     * Save a objetVolee.
     *
     * @param objetVolee the entity to save.
     * @return the persisted entity.
     */
    public ObjetVolee save(ObjetVolee objetVolee) {
        log.debug("Request to save ObjetVolee : {}", objetVolee);
        return objetVoleeRepository.save(objetVolee);
    }

    /**
     * Update a objetVolee.
     *
     * @param objetVolee the entity to save.
     * @return the persisted entity.
     */
    public ObjetVolee update(ObjetVolee objetVolee) {
        log.debug("Request to update ObjetVolee : {}", objetVolee);
        return objetVoleeRepository.save(objetVolee);
    }

    /**
     * Partially update a objetVolee.
     *
     * @param objetVolee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ObjetVolee> partialUpdate(ObjetVolee objetVolee) {
        log.debug("Request to partially update ObjetVolee : {}", objetVolee);

        return objetVoleeRepository
            .findById(objetVolee.getId())
            .map(existingObjetVolee -> {
                if (objetVolee.getType() != null) {
                    existingObjetVolee.setType(objetVolee.getType());
                }
                if (objetVolee.getMarque() != null) {
                    existingObjetVolee.setMarque(objetVolee.getMarque());
                }
                if (objetVolee.getDatevol() != null) {
                    existingObjetVolee.setDatevol(objetVolee.getDatevol());
                }
                if (objetVolee.getPhotoObjet() != null) {
                    existingObjetVolee.setPhotoObjet(objetVolee.getPhotoObjet());
                }
                if (objetVolee.getPhotoObjetContentType() != null) {
                    existingObjetVolee.setPhotoObjetContentType(objetVolee.getPhotoObjetContentType());
                }
                if (objetVolee.getNumeroSerie() != null) {
                    existingObjetVolee.setNumeroSerie(objetVolee.getNumeroSerie());
                }

                return existingObjetVolee;
            })
            .map(objetVoleeRepository::save);
    }

    /**
     * Get all the objetVolees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ObjetVolee> findAll(Pageable pageable) {
        log.debug("Request to get all ObjetVolees");
        return objetVoleeRepository.findAll(pageable);
    }

    /**
     * Get one objetVolee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObjetVolee> findOne(Long id) {
        log.debug("Request to get ObjetVolee : {}", id);
        return objetVoleeRepository.findById(id);
    }

    /**
     * Delete the objetVolee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ObjetVolee : {}", id);
        objetVoleeRepository.deleteById(id);
    }
}
