package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceTestSamples.*;
import static org.enspy.gofind.domain.DeclarationVolTestSamples.*;
import static org.enspy.gofind.domain.LocationTestSamples.*;
import static org.enspy.gofind.domain.TrajetTestSamples.*;
import static org.enspy.gofind.domain.UtilisateurTestSamples.*;

import org.enspy.gofind.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnnonceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Annonce.class);
        Annonce annonce1 = getAnnonceSample1();
        Annonce annonce2 = new Annonce();
        assertThat(annonce1).isNotEqualTo(annonce2);

        annonce2.setId(annonce1.getId());
        assertThat(annonce1).isEqualTo(annonce2);

        annonce2 = getAnnonceSample2();
        assertThat(annonce1).isNotEqualTo(annonce2);
    }

    @Test
    void trajetTest() {
        Annonce annonce = getAnnonceRandomSampleGenerator();
        Trajet trajetBack = getTrajetRandomSampleGenerator();

        annonce.setTrajet(trajetBack);
        assertThat(annonce.getTrajet()).isEqualTo(trajetBack);

        annonce.trajet(null);
        assertThat(annonce.getTrajet()).isNull();
    }

    @Test
    void locationTest() {
        Annonce annonce = getAnnonceRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        annonce.setLocation(locationBack);
        assertThat(annonce.getLocation()).isEqualTo(locationBack);

        annonce.location(null);
        assertThat(annonce.getLocation()).isNull();
    }

    @Test
    void declarationVolTest() {
        Annonce annonce = getAnnonceRandomSampleGenerator();
        DeclarationVol declarationVolBack = getDeclarationVolRandomSampleGenerator();

        annonce.setDeclarationVol(declarationVolBack);
        assertThat(annonce.getDeclarationVol()).isEqualTo(declarationVolBack);

        annonce.declarationVol(null);
        assertThat(annonce.getDeclarationVol()).isNull();
    }

    @Test
    void utilisateurTest() {
        Annonce annonce = getAnnonceRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        annonce.setUtilisateur(utilisateurBack);
        assertThat(annonce.getUtilisateur()).isEqualTo(utilisateurBack);

        annonce.utilisateur(null);
        assertThat(annonce.getUtilisateur()).isNull();
    }
}
