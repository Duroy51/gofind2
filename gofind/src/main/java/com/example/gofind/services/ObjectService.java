package com.example.gofind.services;

import com.example.gofind.repositories.ObjectRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.example.gofind.models.Object;
import java.util.Optional;


@ComponentScan(basePackages = {"com.enspy.gofind.repositories"})
@Data
@Service
public class ObjectService {

    @Autowired
    private ObjectRepository objectRepository;

    public Optional<Object> getObject(final Long id) {
        return objectRepository.findById(id);
    }

    public Iterable<Object> getAllObjects() {
        return objectRepository.findAll();
    }

    public void deleteObject(final Long id) {
        objectRepository.deleteById(id);
    }

    public Object saveObject(Object object) {
        return objectRepository.save(object);
    }
}
