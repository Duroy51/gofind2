package com.example.gofind.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String coordonnee;

    @Column
    private String ville;

    @Column
    private String quartier;

    @Column
    private String duree;

    @Column
    private Long nombrePlace;

    @Column
    private Long prixLocation;

    @ManyToOne
    private User proprietaire_location;

    @ManyToOne
    private User locataire;

}
