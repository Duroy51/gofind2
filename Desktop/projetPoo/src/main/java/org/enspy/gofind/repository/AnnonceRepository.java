package org.enspy.gofind.repository;

import org.enspy.gofind.domain.Annonce;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Annonce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {}
