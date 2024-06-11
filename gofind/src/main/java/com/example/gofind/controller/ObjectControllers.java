package com.example.gofind.controller;

import com.example.gofind.services.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.gofind.models.Object;
import java.util.Optional;

@RestController
public class ObjectControllers {

    @Autowired
    private ObjectService objectService;


    @PostMapping("/object")
    public Object createObject(@RequestBody Object object) {
        return objectService.saveObject(object);
    }



    @GetMapping("/object/{id}")
    public Object getObject(@PathVariable("id") final Long id) {
        Optional<Object> object = objectService.getObject(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            return null;
        }
    }


    @GetMapping("/objects")
    public Iterable<Object> getAllObjects() {
        return objectService.getAllObjects();
    }


    @PutMapping("/object/{id}")
    public Object updateObject(@PathVariable("id") final Long id, @RequestBody Object object) {
        Optional<Object> e = objectService.getObject(id);
        if(e.isPresent()) {
            Object currentObject = e.get();

            String type = object.getType();
            if(type != null) {
                currentObject.setType(type);
            }


            String nom = object.getNom();
            if(nom != null) {
                currentObject.setNom(nom);;
            }


            String marque = object.getMarque();
            if(marque != null) {
                currentObject.setMarque(marque);
            }


            String numeroSerie = object.getNumeroSerie();
            if(numeroSerie != null) {
                currentObject.setNumeroSerie(numeroSerie);
            }




            objectService.saveObject(currentObject);
            return currentObject;
        } else {
            return null;
        }
    }

    @DeleteMapping("/object/{id}")
    public void deleteObject(@PathVariable("id") final Long id) {
        objectService.deleteObject(id);
    }
}
