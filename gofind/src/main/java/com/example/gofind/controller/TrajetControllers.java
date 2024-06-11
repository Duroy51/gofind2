package com.example.gofind.controller;

import com.example.gofind.models.Trajet;
import com.example.gofind.services.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TrajetControllers {

    @Autowired
    private TrajetService trajetService;


    @PostMapping("/trajet")
    public Trajet createTrajet(@RequestBody Trajet trajet) {
        return trajetService.saveTrajet(trajet);
    }



    @GetMapping("/trajet/{id}")
    public Trajet getTrajet(@PathVariable("id") final Long id) {
        Optional<Trajet> trajet = trajetService.getTrajet(id);
        if(trajet.isPresent()) {
            return trajet.get();
        } else {
            return null;
        }
    }


    @GetMapping("/trajets")
    public Iterable<Trajet> getAllTrajets() {
        return trajetService.getAllTrajets();
    }


    @PutMapping("/trajet/{id}")
    public Trajet updateTrajet(@PathVariable("id") final Long id, @RequestBody Trajet trajet) {
        Optional<Trajet> e = trajetService.getTrajet(id);
        if(e.isPresent()) {
            Trajet currentTrajet = e.get();

            String destinationDepart = trajet.getDestinationDepart();
            if(destinationDepart != null) {
                currentTrajet.setDestinationDepart(destinationDepart);
            }


            String destinationArrivee = trajet.getDestinationArrivee();
            if(destinationArrivee != null) {
                currentTrajet.setDestinationArrivee(destinationArrivee);
            }


            String heureDepart = trajet.getHeureDepart();
            if(heureDepart != null) {
                currentTrajet.setHeureDepart(heureDepart);
            }


            String heureArrivee = trajet.getHeureArrivee();
            if(heureArrivee != null) {
                currentTrajet.setHeureArrivee(heureArrivee);
            }


            Long nombrePlace = trajet.getNombrePlace();
            if(nombrePlace != null) {
                currentTrajet.setNombrePlace(nombrePlace);
            }


            Long prix = trajet.getPrix();
            if(prix != null) {
                currentTrajet.setPrix(prix);
            }



            trajetService.saveTrajet(currentTrajet);
            return currentTrajet;
        } else {
            return null;
        }
    }

    @DeleteMapping("/trajet/{id}")
    public void deleteTrajet(@PathVariable("id") final Long id) {
        trajetService.deleteTrajet(id);
    }
}
