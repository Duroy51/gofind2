package com.example.gofind.controller;

import com.example.gofind.models.Location;
import com.example.gofind.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LocationControllers {


    @Autowired
    private LocationService locationService;


    @PostMapping("/location")
    public Location createLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }



    @GetMapping("/employee/{id}")
    public Location getLocation(@PathVariable("id") final Long id) {
        Optional<Location> location = locationService.getLocation(id);
        if(location.isPresent()) {
            return location.get();
        } else {
            return null;
        }
    }


    @GetMapping("/locations")
    public Iterable<Location> getAllLocations() {
        return locationService.getAllLocations();
    }


    @PutMapping("/location/{id}")
    public Location updateEmployee(@PathVariable("id") final Long id, @RequestBody Location location) {
        Optional<Location> e = locationService.getLocation(id);
        if(e.isPresent()) {
            Location currentLocation = e.get();

            String coordonnee = location.getCoordonnee();
            if(coordonnee != null) {
                currentLocation.setCoordonnee(coordonnee);
            }


            String ville = location.getVille();
            if(ville != null) {
                currentLocation.setVille(ville);;
            }


            String quartier = location.getQuartier();
            if(quartier != null) {
                currentLocation.setQuartier(quartier);
            }


            String duree = location.getDuree();
            if(duree != null) {
                currentLocation.setDuree(duree);
            }


            Long nombrePlace = location.getNombrePlace();
            if(nombrePlace != null) {
                currentLocation.setNombrePlace(nombrePlace);
            }


            Long prixLocation = location.getPrixLocation();
            if(prixLocation != null) {
                currentLocation.setPrixLocation(prixLocation);
            }



            locationService.saveLocation(currentLocation);
            return currentLocation;
        } else {
            return null;
        }
    }

    @DeleteMapping("/location/{id}")
    public void deleteEmployee(@PathVariable("id") final Long id) {
        locationService.deleteLocation(id);
    }
}
