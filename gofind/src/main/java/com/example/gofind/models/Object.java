package com.example.gofind.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Object {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String nom;

    @Column
    private String marque;

    @Column
    private String numeroSerie;

    @ManyToOne
    private  User proprietaire_objet;

}
