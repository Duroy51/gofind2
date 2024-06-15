package com.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trajet.
 */
@Entity
@Table(name = "trajet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "heuredepart")
    private String heuredepart;

    @Column(name = "heurearrivee")
    private String heurearrivee;

    @Column(name = "lieudepart")
    private String lieudepart;

    @Column(name = "lieuarrivee")
    private String lieuarrivee;

    @Column(name = "nombreplace")
    private Integer nombreplace;

    @Column(name = "prixplace")
    private Double prixplace;

    @Lob
    @Column(name = "image_vehicule")
    private byte[] imageVehicule;

    @Column(name = "image_vehicule_content_type")
    private String imageVehiculeContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "objetVolees", "locations", "trajets" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Trajet date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHeuredepart() {
        return this.heuredepart;
    }

    public Trajet heuredepart(String heuredepart) {
        this.setHeuredepart(heuredepart);
        return this;
    }

    public void setHeuredepart(String heuredepart) {
        this.heuredepart = heuredepart;
    }

    public String getHeurearrivee() {
        return this.heurearrivee;
    }

    public Trajet heurearrivee(String heurearrivee) {
        this.setHeurearrivee(heurearrivee);
        return this;
    }

    public void setHeurearrivee(String heurearrivee) {
        this.heurearrivee = heurearrivee;
    }

    public String getLieudepart() {
        return this.lieudepart;
    }

    public Trajet lieudepart(String lieudepart) {
        this.setLieudepart(lieudepart);
        return this;
    }

    public void setLieudepart(String lieudepart) {
        this.lieudepart = lieudepart;
    }

    public String getLieuarrivee() {
        return this.lieuarrivee;
    }

    public Trajet lieuarrivee(String lieuarrivee) {
        this.setLieuarrivee(lieuarrivee);
        return this;
    }

    public void setLieuarrivee(String lieuarrivee) {
        this.lieuarrivee = lieuarrivee;
    }

    public Integer getNombreplace() {
        return this.nombreplace;
    }

    public Trajet nombreplace(Integer nombreplace) {
        this.setNombreplace(nombreplace);
        return this;
    }

    public void setNombreplace(Integer nombreplace) {
        this.nombreplace = nombreplace;
    }

    public Double getPrixplace() {
        return this.prixplace;
    }

    public Trajet prixplace(Double prixplace) {
        this.setPrixplace(prixplace);
        return this;
    }

    public void setPrixplace(Double prixplace) {
        this.prixplace = prixplace;
    }

    public byte[] getImageVehicule() {
        return this.imageVehicule;
    }

    public Trajet imageVehicule(byte[] imageVehicule) {
        this.setImageVehicule(imageVehicule);
        return this;
    }

    public void setImageVehicule(byte[] imageVehicule) {
        this.imageVehicule = imageVehicule;
    }

    public String getImageVehiculeContentType() {
        return this.imageVehiculeContentType;
    }

    public Trajet imageVehiculeContentType(String imageVehiculeContentType) {
        this.imageVehiculeContentType = imageVehiculeContentType;
        return this;
    }

    public void setImageVehiculeContentType(String imageVehiculeContentType) {
        this.imageVehiculeContentType = imageVehiculeContentType;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Trajet utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajet)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajet{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", heuredepart='" + getHeuredepart() + "'" +
            ", heurearrivee='" + getHeurearrivee() + "'" +
            ", lieudepart='" + getLieudepart() + "'" +
            ", lieuarrivee='" + getLieuarrivee() + "'" +
            ", nombreplace=" + getNombreplace() +
            ", prixplace=" + getPrixplace() +
            ", imageVehicule='" + getImageVehicule() + "'" +
            ", imageVehiculeContentType='" + getImageVehiculeContentType() + "'" +
            "}";
    }
}
