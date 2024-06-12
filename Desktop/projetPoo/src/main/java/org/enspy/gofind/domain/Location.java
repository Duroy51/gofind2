package org.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
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

    @Column(name = "datedebut")
    private Instant datedebut;

    @Column(name = "datefin")
    private Instant datefin;

    @Column(name = "prix")
    private Double prix;

    @JsonIgnoreProperties(value = { "trajet", "location", "declarationVol", "utilisateur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private Annonce annonce;

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

    public Instant getDatedebut() {
        return this.datedebut;
    }

    public Location datedebut(Instant datedebut) {
        this.setDatedebut(datedebut);
        return this;
    }

    public void setDatedebut(Instant datedebut) {
        this.datedebut = datedebut;
    }

    public Instant getDatefin() {
        return this.datefin;
    }

    public Location datefin(Instant datefin) {
        this.setDatefin(datefin);
        return this;
    }

    public void setDatefin(Instant datefin) {
        this.datefin = datefin;
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

    public Annonce getAnnonce() {
        return this.annonce;
    }

    public void setAnnonce(Annonce annonce) {
        if (this.annonce != null) {
            this.annonce.setLocation(null);
        }
        if (annonce != null) {
            annonce.setLocation(this);
        }
        this.annonce = annonce;
    }

    public Location annonce(Annonce annonce) {
        this.setAnnonce(annonce);
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
            ", datedebut='" + getDatedebut() + "'" +
            ", datefin='" + getDatefin() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
