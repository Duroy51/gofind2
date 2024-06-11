package com.example.gofind.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.gofind.models.Object;
@Repository
public interface ObjectRepository extends CrudRepository<Object, Long> {

}
