package com.enspy.gofind.repository;

import com.enspy.gofind.domain.ObjetVolee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ObjetVolee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjetVoleeRepository extends JpaRepository<ObjetVolee, Long> {}
