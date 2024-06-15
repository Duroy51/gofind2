package com.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ObjetVolee.
 */
@Entity
@Table(name = "objet_volee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObjetVolee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "marque")
    private String marque;

    @Column(name = "datevol")
    private LocalDate datevol;

    @Lob
    @Column(name = "photo_objet")
    private byte[] photoObjet;

    @Column(name = "photo_objet_content_type")
    private String photoObjetContentType;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "objetVolees", "locations", "trajets" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ObjetVolee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public ObjetVolee type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarque() {
        return this.marque;
    }

    public ObjetVolee marque(String marque) {
        this.setMarque(marque);
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public LocalDate getDatevol() {
        return this.datevol;
    }

    public ObjetVolee datevol(LocalDate datevol) {
        this.setDatevol(datevol);
        return this;
    }

    public void setDatevol(LocalDate datevol) {
        this.datevol = datevol;
    }

    public byte[] getPhotoObjet() {
        return this.photoObjet;
    }

    public ObjetVolee photoObjet(byte[] photoObjet) {
        this.setPhotoObjet(photoObjet);
        return this;
    }

    public void setPhotoObjet(byte[] photoObjet) {
        this.photoObjet = photoObjet;
    }

    public String getPhotoObjetContentType() {
        return this.photoObjetContentType;
    }

    public ObjetVolee photoObjetContentType(String photoObjetContentType) {
        this.photoObjetContentType = photoObjetContentType;
        return this;
    }

    public void setPhotoObjetContentType(String photoObjetContentType) {
        this.photoObjetContentType = photoObjetContentType;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public ObjetVolee numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ObjetVolee utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjetVolee)) {
            return false;
        }
        return getId() != null && getId().equals(((ObjetVolee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjetVolee{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", marque='" + getMarque() + "'" +
            ", datevol='" + getDatevol() + "'" +
            ", photoObjet='" + getPhotoObjet() + "'" +
            ", photoObjetContentType='" + getPhotoObjetContentType() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            "}";
    }
}
