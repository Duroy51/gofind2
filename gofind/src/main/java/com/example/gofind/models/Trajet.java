package com.example.gofind.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table
public class Trajet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String destinationDepart;

    @Column
    private String destinationArrivee;

    @Column
    private String heureDepart;

    @Column
    private String heureArrivee;

    @Column
    private Long nombrePlace;

    @Column
    private  Long prix;

    @ManyToOne
    private User chauffeur;

    @ManyToMany
    private Set<User> covoitureur;
}
