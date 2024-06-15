package com.enspy.gofind.domain;

import static com.enspy.gofind.domain.TrajetTestSamples.*;
import static com.enspy.gofind.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.enspy.gofind.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajet.class);
        Trajet trajet1 = getTrajetSample1();
        Trajet trajet2 = new Trajet();
        assertThat(trajet1).isNotEqualTo(trajet2);

        trajet2.setId(trajet1.getId());
        assertThat(trajet1).isEqualTo(trajet2);

        trajet2 = getTrajetSample2();
        assertThat(trajet1).isNotEqualTo(trajet2);
    }

    @Test
    void utilisateurTest() {
        Trajet trajet = getTrajetRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        trajet.setUtilisateur(utilisateurBack);
        assertThat(trajet.getUtilisateur()).isEqualTo(utilisateurBack);

        trajet.utilisateur(null);
        assertThat(trajet.getUtilisateur()).isNull();
    }
}
