package org.enspy.gofind.service;

import java.util.Optional;
import org.enspy.gofind.domain.Utilisateur;
import org.enspy.gofind.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.enspy.gofind.domain.Utilisateur}.
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
                if (utilisateur.getNom() != null) {
                    existingUtilisateur.setNom(utilisateur.getNom());
                }
                if (utilisateur.getPrenom() != null) {
                    existingUtilisateur.setPrenom(utilisateur.getPrenom());
                }
                if (utilisateur.getEmail() != null) {
                    existingUtilisateur.setEmail(utilisateur.getEmail());
                }
                if (utilisateur.getTelephone() != null) {
                    existingUtilisateur.setTelephone(utilisateur.getTelephone());
                }
                if (utilisateur.getPassword() != null) {
                    existingUtilisateur.setPassword(utilisateur.getPassword());
                }

                return existingUtilisateur;
            })
            .map(utilisateurRepository::save);
    }

    /**
     * Get all the utilisateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Utilisateur> findAll(Pageable pageable) {
        log.debug("Request to get all Utilisateurs");
        return utilisateurRepository.findAll(pageable);
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
