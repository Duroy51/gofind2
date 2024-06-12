package org.enspy.gofind.repository;

import org.enspy.gofind.domain.DeclarationVol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DeclarationVol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclarationVolRepository extends JpaRepository<DeclarationVol, Long> {}
