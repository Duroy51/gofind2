package com.enspy.gofind.service;

import com.enspy.gofind.domain.Utilisateur;
import com.enspy.gofind.repository.UtilisateurRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.enspy.gofind.domain.Utilisateur}.
 */
@Service
@Transactional
public class UtilisateurService {

    private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Save a utilisateur.
     *
     * @param utilisateur the entity to save.
     * @return the persisted entity.
     */
    public Utilisateur save(Utilisateur utilisateur) {
        log.debug("Request to save Utilisateur : {}", utilisateur);
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Update a utilisateur.
     *
     * @param utilisateur the entity to save.
     * @return the persisted entity.
     */
    public Utilisateur update(Utilisateur utilisateur) {
        log.debug("Request to update Utilisateur : {}", utilisateur);
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Partially update a utilisateur.
     *
     * @param utilisateur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Utilisateur> partialUpdate(Utilisateur utilisateur) {
        log.debug("Request to partially update Utilisateur : {}", utilisateur);

        return utilisateurRepository
            .findById(utilisateur.getId())
            .map(existingUtilisateur -> {
                if (utilisateur.getUserName() != null) {
                    existingUtilisateur.setUserName(utilisateur.getUserName());
                }
                if (utilisateur.getEmail() != null) {
                    existingUtilisateur.setEmail(utilisateur.getEmail());
                }

                return existingUtilisateur;
            })
            .map(utilisateurRepository::save);
    }

    /**
     * Get all the utilisateurs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> findAll() {
        log.debug("Request to get all Utilisateurs");
        return utilisateurRepository.findAll();
    }

    /**
     * Get one utilisateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findOne(Long id) {
        log.debug("Request to get Utilisateur : {}", id);
        return utilisateurRepository.findById(id);
    }

    /**
     * Delete the utilisateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Utilisateur : {}", id);
        utilisateurRepository.deleteById(id);
    }
}
