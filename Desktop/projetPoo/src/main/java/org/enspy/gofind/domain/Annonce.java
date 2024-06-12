package org.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Annonce.
 */
@Entity
@Table(name = "annonce")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Annonce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "statut")
    private String statut;

    @Column(name = "datecreation")
    private Instant datecreation;

    @JsonIgnoreProperties(value = { "annonce" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Trajet trajet;

    @JsonIgnoreProperties(value = { "annonce" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Location location;

    @JsonIgnoreProperties(value = { "annonce" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private DeclarationVol declarationVol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "annonces" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Annonce id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public Annonce titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return this.description;
    }

    public Annonce description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public Annonce type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return this.statut;
    }

    public Annonce statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Instant getDatecreation() {
        return this.datecreation;
    }

    public Annonce datecreation(Instant datecreation) {
        this.setDatecreation(datecreation);
        return this;
    }

    public void setDatecreation(Instant datecreation) {
        this.datecreation = datecreation;
    }

    public Trajet getTrajet() {
        return this.trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Annonce trajet(Trajet trajet) {
        this.setTrajet(trajet);
        return this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Annonce location(Location location) {
        this.setLocation(location);
        return this;
    }

    public DeclarationVol getDeclarationVol() {
        return this.declarationVol;
    }

    public void setDeclarationVol(DeclarationVol declarationVol) {
        this.declarationVol = declarationVol;
    }

    public Annonce declarationVol(DeclarationVol declarationVol) {
        this.setDeclarationVol(declarationVol);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Annonce utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annonce)) {
            return false;
        }
        return getId() != null && getId().equals(((Annonce) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Annonce{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", statut='" + getStatut() + "'" +
            ", datecreation='" + getDatecreation() + "'" +
            "}";
    }
}
