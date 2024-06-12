package org.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeclarationVol.
 */
@Entity
@Table(name = "declaration_vol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeclarationVol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(name = "numeroserie")
    private String numeroserie;

    @Column(name = "datevol")
    private Instant datevol;

    @JsonIgnoreProperties(value = { "trajet", "location", "declarationVol", "utilisateur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "declarationVol")
    private Annonce annonce;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeclarationVol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public DeclarationVol type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarque() {
        return this.marque;
    }

    public DeclarationVol marque(String marque) {
        this.setMarque(marque);
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return this.modele;
    }

    public DeclarationVol modele(String modele) {
        this.setModele(modele);
        return this;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getNumeroserie() {
        return this.numeroserie;
    }

    public DeclarationVol numeroserie(String numeroserie) {
        this.setNumeroserie(numeroserie);
        return this;
    }

    public void setNumeroserie(String numeroserie) {
        this.numeroserie = numeroserie;
    }

    public Instant getDatevol() {
        return this.datevol;
    }

    public DeclarationVol datevol(Instant datevol) {
        this.setDatevol(datevol);
        return this;
    }

    public void setDatevol(Instant datevol) {
        this.datevol = datevol;
    }

    public Annonce getAnnonce() {
        return this.annonce;
    }

    public void setAnnonce(Annonce annonce) {
        if (this.annonce != null) {
            this.annonce.setDeclarationVol(null);
        }
        if (annonce != null) {
            annonce.setDeclarationVol(this);
        }
        this.annonce = annonce;
    }

    public DeclarationVol annonce(Annonce annonce) {
        this.setAnnonce(annonce);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeclarationVol)) {
            return false;
        }
        return getId() != null && getId().equals(((DeclarationVol) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeclarationVol{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", marque='" + getMarque() + "'" +
            ", modele='" + getModele() + "'" +
            ", numeroserie='" + getNumeroserie() + "'" +
            ", datevol='" + getDatevol() + "'" +
            "}";
    }
}
