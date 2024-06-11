package com.example.gofind.repositories;

import com.example.gofind.models.Trajet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends CrudRepository<Trajet, Long> {
}
