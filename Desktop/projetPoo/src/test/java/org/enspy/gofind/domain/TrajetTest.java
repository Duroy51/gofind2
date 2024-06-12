package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceTestSamples.*;
import static org.enspy.gofind.domain.TrajetTestSamples.*;

import org.enspy.gofind.web.rest.TestUtil;
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
    void annonceTest() {
        Trajet trajet = getTrajetRandomSampleGenerator();
        Annonce annonceBack = getAnnonceRandomSampleGenerator();

        trajet.setAnnonce(annonceBack);
        assertThat(trajet.getAnnonce()).isEqualTo(annonceBack);
        assertThat(annonceBack.getTrajet()).isEqualTo(trajet);

        trajet.annonce(null);
        assertThat(trajet.getAnnonce()).isNull();
        assertThat(annonceBack.getTrajet()).isNull();
    }
}
