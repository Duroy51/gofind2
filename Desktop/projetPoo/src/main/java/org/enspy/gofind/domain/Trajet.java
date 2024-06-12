package org.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
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
    private Instant date;

    @Column(name = "heuredepart")
    private Instant heuredepart;

    @Column(name = "heurearrivee")
    private Instant heurearrivee;

    @Column(name = "lieudepart")
    private String lieudepart;

    @Column(name = "lieuarrivee")
    private String lieuarrivee;

    @Column(name = "nombreplace")
    private Integer nombreplace;

    @Column(name = "prixplace")
    private Double prixplace;

    @Column(name = "marque_vehicule")
    private String marqueVehicule;

    @JsonIgnoreProperties(value = { "trajet", "location", "declarationVol", "utilisateur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "trajet")
    private Annonce annonce;

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

    public Instant getDate() {
        return this.date;
    }

    public Trajet date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Instant getHeuredepart() {
        return this.heuredepart;
    }

    public Trajet heuredepart(Instant heuredepart) {
        this.setHeuredepart(heuredepart);
        return this;
    }

    public void setHeuredepart(Instant heuredepart) {
        this.heuredepart = heuredepart;
    }

    public Instant getHeurearrivee() {
        return this.heurearrivee;
    }

    public Trajet heurearrivee(Instant heurearrivee) {
        this.setHeurearrivee(heurearrivee);
        return this;
    }

    public void setHeurearrivee(Instant heurearrivee) {
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

    public String getMarqueVehicule() {
        return this.marqueVehicule;
    }

    public Trajet marqueVehicule(String marqueVehicule) {
        this.setMarqueVehicule(marqueVehicule);
        return this;
    }

    public void setMarqueVehicule(String marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
    }

    public Annonce getAnnonce() {
        return this.annonce;
    }

    public void setAnnonce(Annonce annonce) {
        if (this.annonce != null) {
            this.annonce.setTrajet(null);
        }
        if (annonce != null) {
            annonce.setTrajet(this);
        }
        this.annonce = annonce;
    }

    public Trajet annonce(Annonce annonce) {
        this.setAnnonce(annonce);
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
            ", marqueVehicule='" + getMarqueVehicule() + "'" +
            "}";
    }
}
