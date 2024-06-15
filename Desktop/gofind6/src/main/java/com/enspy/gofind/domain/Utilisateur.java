package com.enspy.gofind.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<ObjetVolee> objetVolees = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Trajet> trajets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public Utilisateur userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Utilisateur user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<ObjetVolee> getObjetVolees() {
        return this.objetVolees;
    }

    public void setObjetVolees(Set<ObjetVolee> objetVolees) {
        if (this.objetVolees != null) {
            this.objetVolees.forEach(i -> i.setUtilisateur(null));
        }
        if (objetVolees != null) {
            objetVolees.forEach(i -> i.setUtilisateur(this));
        }
        this.objetVolees = objetVolees;
    }

    public Utilisateur objetVolees(Set<ObjetVolee> objetVolees) {
        this.setObjetVolees(objetVolees);
        return this;
    }

    public Utilisateur addObjetVolee(ObjetVolee objetVolee) {
        this.objetVolees.add(objetVolee);
        objetVolee.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeObjetVolee(ObjetVolee objetVolee) {
        this.objetVolees.remove(objetVolee);
        objetVolee.setUtilisateur(null);
        return this;
    }

    public Set<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(Set<Location> locations) {
        if (this.locations != null) {
            this.locations.forEach(i -> i.setUtilisateur(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setUtilisateur(this));
        }
        this.locations = locations;
    }

    public Utilisateur locations(Set<Location> locations) {
        this.setLocations(locations);
        return this;
    }

    public Utilisateur addLocation(Location location) {
        this.locations.add(location);
        location.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeLocation(Location location) {
        this.locations.remove(location);
        location.setUtilisateur(null);
        return this;
    }

    public Set<Trajet> getTrajets() {
        return this.trajets;
    }

    public void setTrajets(Set<Trajet> trajets) {
        if (this.trajets != null) {
            this.trajets.forEach(i -> i.setUtilisateur(null));
        }
        if (trajets != null) {
            trajets.forEach(i -> i.setUtilisateur(this));
        }
        this.trajets = trajets;
    }

    public Utilisateur trajets(Set<Trajet> trajets) {
        this.setTrajets(trajets);
        return this;
    }

    public Utilisateur addTrajet(Trajet trajet) {
        this.trajets.add(trajet);
        trajet.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeTrajet(Trajet trajet) {
        this.trajets.remove(trajet);
        trajet.setUtilisateur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return getId() != null && getId().equals(((Utilisateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
