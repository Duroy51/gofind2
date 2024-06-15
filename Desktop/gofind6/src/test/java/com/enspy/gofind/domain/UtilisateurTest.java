package com.enspy.gofind.domain;

import static com.enspy.gofind.domain.LocationTestSamples.*;
import static com.enspy.gofind.domain.ObjetVoleeTestSamples.*;
import static com.enspy.gofind.domain.TrajetTestSamples.*;
import static com.enspy.gofind.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.enspy.gofind.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UtilisateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = getUtilisateurSample1();
        Utilisateur utilisateur2 = new Utilisateur();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);

        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);

        utilisateur2 = getUtilisateurSample2();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }

    @Test
    void objetVoleeTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        ObjetVolee objetVoleeBack = getObjetVoleeRandomSampleGenerator();

        utilisateur.addObjetVolee(objetVoleeBack);
        assertThat(utilisateur.getObjetVolees()).containsOnly(objetVoleeBack);
        assertThat(objetVoleeBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeObjetVolee(objetVoleeBack);
        assertThat(utilisateur.getObjetVolees()).doesNotContain(objetVoleeBack);
        assertThat(objetVoleeBack.getUtilisateur()).isNull();

        utilisateur.objetVolees(new HashSet<>(Set.of(objetVoleeBack)));
        assertThat(utilisateur.getObjetVolees()).containsOnly(objetVoleeBack);
        assertThat(objetVoleeBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setObjetVolees(new HashSet<>());
        assertThat(utilisateur.getObjetVolees()).doesNotContain(objetVoleeBack);
        assertThat(objetVoleeBack.getUtilisateur()).isNull();
    }

    @Test
    void locationTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        utilisateur.addLocation(locationBack);
        assertThat(utilisateur.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeLocation(locationBack);
        assertThat(utilisateur.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getUtilisateur()).isNull();

        utilisateur.locations(new HashSet<>(Set.of(locationBack)));
        assertThat(utilisateur.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setLocations(new HashSet<>());
        assertThat(utilisateur.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getUtilisateur()).isNull();
    }

    @Test
    void trajetTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Trajet trajetBack = getTrajetRandomSampleGenerator();

        utilisateur.addTrajet(trajetBack);
        assertThat(utilisateur.getTrajets()).containsOnly(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeTrajet(trajetBack);
        assertThat(utilisateur.getTrajets()).doesNotContain(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isNull();

        utilisateur.trajets(new HashSet<>(Set.of(trajetBack)));
        assertThat(utilisateur.getTrajets()).containsOnly(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setTrajets(new HashSet<>());
        assertThat(utilisateur.getTrajets()).doesNotContain(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isNull();
    }
}
