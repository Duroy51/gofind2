package org.enspy.gofind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.enspy.gofind.domain.Trajet;
import org.enspy.gofind.repository.TrajetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.enspy.gofind.domain.Trajet}.
 */
@Service
@Transactional
public class TrajetService {

    private final Logger log = LoggerFactory.getLogger(TrajetService.class);

    private final TrajetRepository trajetRepository;

    public TrajetService(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    /**
     * Save a trajet.
     *
     * @param trajet the entity to save.
     * @return the persisted entity.
     */
    public Trajet save(Trajet trajet) {
        log.debug("Request to save Trajet : {}", trajet);
        return trajetRepository.save(trajet);
    }

    /**
     * Update a trajet.
     *
     * @param trajet the entity to save.
     * @return the persisted entity.
     */
    public Trajet update(Trajet trajet) {
        log.debug("Request to update Trajet : {}", trajet);
        return trajetRepository.save(trajet);
    }

    /**
     * Partially update a trajet.
     *
     * @param trajet the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Trajet> partialUpdate(Trajet trajet) {
        log.debug("Request to partially update Trajet : {}", trajet);

        return trajetRepository
            .findById(trajet.getId())
            .map(existingTrajet -> {
                if (trajet.getDate() != null) {
                    existingTrajet.setDate(trajet.getDate());
                }
                if (trajet.getHeuredepart() != null) {
                    existingTrajet.setHeuredepart(trajet.getHeuredepart());
                }
                if (trajet.getHeurearrivee() != null) {
                    existingTrajet.setHeurearrivee(trajet.getHeurearrivee());
                }
                if (trajet.getLieudepart() != null) {
                    existingTrajet.setLieudepart(trajet.getLieudepart());
                }
                if (trajet.getLieuarrivee() != null) {
                    existingTrajet.setLieuarrivee(trajet.getLieuarrivee());
                }
                if (trajet.getNombreplace() != null) {
                    existingTrajet.setNombreplace(trajet.getNombreplace());
                }
                if (trajet.getPrixplace() != null) {
                    existingTrajet.setPrixplace(trajet.getPrixplace());
                }
                if (trajet.getMarqueVehicule() != null) {
                    existingTrajet.setMarqueVehicule(trajet.getMarqueVehicule());
                }

                return existingTrajet;
            })
            .map(trajetRepository::save);
    }

    /**
     * Get all the trajets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Trajet> findAll(Pageable pageable) {
        log.debug("Request to get all Trajets");
        return trajetRepository.findAll(pageable);
    }

    /**
     *  Get all the trajets where Annonce is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Trajet> findAllWhereAnnonceIsNull() {
        log.debug("Request to get all trajets where Annonce is null");
        return StreamSupport.stream(trajetRepository.findAll().spliterator(), false).filter(trajet -> trajet.getAnnonce() == null).toList();
    }

    /**
     * Get one trajet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Trajet> findOne(Long id) {
        log.debug("Request to get Trajet : {}", id);
        return trajetRepository.findById(id);
    }

    /**
     * Delete the trajet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trajet : {}", id);
        trajetRepository.deleteById(id);
    }
}
