package org.enspy.gofind.repository;

import org.enspy.gofind.domain.Trajet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Trajet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {}
