package org.enspy.gofind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.enspy.gofind.domain.Location;
import org.enspy.gofind.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.enspy.gofind.domain.Location}.
 */
@Service
@Transactional
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Save a location.
     *
     * @param location the entity to save.
     * @return the persisted entity.
     */
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    /**
     * Update a location.
     *
     * @param location the entity to save.
     * @return the persisted entity.
     */
    public Location update(Location location) {
        log.debug("Request to update Location : {}", location);
        return locationRepository.save(location);
    }

    /**
     * Partially update a location.
     *
     * @param location the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Location> partialUpdate(Location location) {
        log.debug("Request to partially update Location : {}", location);

        return locationRepository
            .findById(location.getId())
            .map(existingLocation -> {
                if (location.getDatedebut() != null) {
                    existingLocation.setDatedebut(location.getDatedebut());
                }
                if (location.getDatefin() != null) {
                    existingLocation.setDatefin(location.getDatefin());
                }
                if (location.getPrix() != null) {
                    existingLocation.setPrix(location.getPrix());
                }

                return existingLocation;
            })
            .map(locationRepository::save);
    }

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Location> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable);
    }

    /**
     *  Get all the locations where Annonce is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Location> findAllWhereAnnonceIsNull() {
        log.debug("Request to get all locations where Annonce is null");
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
            .filter(location -> location.getAnnonce() == null)
            .toList();
    }

    /**
     * Get one location by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
