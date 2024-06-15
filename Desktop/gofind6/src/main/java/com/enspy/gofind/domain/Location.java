package com.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo_pieces")
    private byte[] photoPieces;

    @Column(name = "photo_pieces_content_type")
    private String photoPiecesContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "objetVolees", "locations", "trajets" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieu() {
        return this.lieu;
    }

    public Location lieu(String lieu) {
        this.setLieu(lieu);
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getPrix() {
        return this.prix;
    }

    public Location prix(Double prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return this.description;
    }

    public Location description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhotoPieces() {
        return this.photoPieces;
    }

    public Location photoPieces(byte[] photoPieces) {
        this.setPhotoPieces(photoPieces);
        return this;
    }

    public void setPhotoPieces(byte[] photoPieces) {
        this.photoPieces = photoPieces;
    }

    public String getPhotoPiecesContentType() {
        return this.photoPiecesContentType;
    }

    public Location photoPiecesContentType(String photoPiecesContentType) {
        this.photoPiecesContentType = photoPiecesContentType;
        return this;
    }

    public void setPhotoPiecesContentType(String photoPiecesContentType) {
        this.photoPiecesContentType = photoPiecesContentType;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Location utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", lieu='" + getLieu() + "'" +
            ", prix=" + getPrix() +
            ", description='" + getDescription() + "'" +
            ", photoPieces='" + getPhotoPieces() + "'" +
            ", photoPiecesContentType='" + getPhotoPiecesContentType() + "'" +
            "}";
    }
}
