package com.enspy.gofind.domain;

import static com.enspy.gofind.domain.ObjetVoleeTestSamples.*;
import static com.enspy.gofind.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.enspy.gofind.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjetVoleeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObjetVolee.class);
        ObjetVolee objetVolee1 = getObjetVoleeSample1();
        ObjetVolee objetVolee2 = new ObjetVolee();
        assertThat(objetVolee1).isNotEqualTo(objetVolee2);

        objetVolee2.setId(objetVolee1.getId());
        assertThat(objetVolee1).isEqualTo(objetVolee2);

        objetVolee2 = getObjetVoleeSample2();
        assertThat(objetVolee1).isNotEqualTo(objetVolee2);
    }

    @Test
    void utilisateurTest() {
        ObjetVolee objetVolee = getObjetVoleeRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        objetVolee.setUtilisateur(utilisateurBack);
        assertThat(objetVolee.getUtilisateur()).isEqualTo(utilisateurBack);

        objetVolee.utilisateur(null);
        assertThat(objetVolee.getUtilisateur()).isNull();
    }
}
