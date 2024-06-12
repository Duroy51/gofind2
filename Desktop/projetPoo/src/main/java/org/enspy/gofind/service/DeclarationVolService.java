package org.enspy.gofind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.enspy.gofind.domain.DeclarationVol;
import org.enspy.gofind.repository.DeclarationVolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.enspy.gofind.domain.DeclarationVol}.
 */
@Service
@Transactional
public class DeclarationVolService {

    private final Logger log = LoggerFactory.getLogger(DeclarationVolService.class);

    private final DeclarationVolRepository declarationVolRepository;

    public DeclarationVolService(DeclarationVolRepository declarationVolRepository) {
        this.declarationVolRepository = declarationVolRepository;
    }

    /**
     * Save a declarationVol.
     *
     * @param declarationVol the entity to save.
     * @return the persisted entity.
     */
    public DeclarationVol save(DeclarationVol declarationVol) {
        log.debug("Request to save DeclarationVol : {}", declarationVol);
        return declarationVolRepository.save(declarationVol);
    }

    /**
     * Update a declarationVol.
     *
     * @param declarationVol the entity to save.
     * @return the persisted entity.
     */
    public DeclarationVol update(DeclarationVol declarationVol) {
        log.debug("Request to update DeclarationVol : {}", declarationVol);
        return declarationVolRepository.save(declarationVol);
    }

    /**
     * Partially update a declarationVol.
     *
     * @param declarationVol the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeclarationVol> partialUpdate(DeclarationVol declarationVol) {
        log.debug("Request to partially update DeclarationVol : {}", declarationVol);

        return declarationVolRepository
            .findById(declarationVol.getId())
            .map(existingDeclarationVol -> {
                if (declarationVol.getType() != null) {
                    existingDeclarationVol.setType(declarationVol.getType());
                }
                if (declarationVol.getMarque() != null) {
                    existingDeclarationVol.setMarque(declarationVol.getMarque());
                }
                if (declarationVol.getModele() != null) {
                    existingDeclarationVol.setModele(declarationVol.getModele());
                }
                if (declarationVol.getNumeroserie() != null) {
                    existingDeclarationVol.setNumeroserie(declarationVol.getNumeroserie());
                }
                if (declarationVol.getDatevol() != null) {
                    existingDeclarationVol.setDatevol(declarationVol.getDatevol());
                }

                return existingDeclarationVol;
            })
            .map(declarationVolRepository::save);
    }

    /**
     * Get all the declarationVols.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeclarationVol> findAll(Pageable pageable) {
        log.debug("Request to get all DeclarationVols");
        return declarationVolRepository.findAll(pageable);
    }

    /**
     *  Get all the declarationVols where Annonce is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeclarationVol> findAllWhereAnnonceIsNull() {
        log.debug("Request to get all declarationVols where Annonce is null");
        return StreamSupport.stream(declarationVolRepository.findAll().spliterator(), false)
            .filter(declarationVol -> declarationVol.getAnnonce() == null)
            .toList();
    }

    /**
     * Get one declarationVol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeclarationVol> findOne(Long id) {
        log.debug("Request to get DeclarationVol : {}", id);
        return declarationVolRepository.findById(id);
    }

    /**
     * Delete the declarationVol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeclarationVol : {}", id);
        declarationVolRepository.deleteById(id);
    }
}
