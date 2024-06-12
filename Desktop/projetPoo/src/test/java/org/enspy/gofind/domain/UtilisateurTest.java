package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceTestSamples.*;
import static org.enspy.gofind.domain.UtilisateurTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.enspy.gofind.web.rest.TestUtil;
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
    void annonceTest() {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Annonce annonceBack = getAnnonceRandomSampleGenerator();

        utilisateur.addAnnonce(annonceBack);
        assertThat(utilisateur.getAnnonces()).containsOnly(annonceBack);
        assertThat(annonceBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeAnnonce(annonceBack);
        assertThat(utilisateur.getAnnonces()).doesNotContain(annonceBack);
        assertThat(annonceBack.getUtilisateur()).isNull();

        utilisateur.annonces(new HashSet<>(Set.of(annonceBack)));
        assertThat(utilisateur.getAnnonces()).containsOnly(annonceBack);
        assertThat(annonceBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setAnnonces(new HashSet<>());
        assertThat(utilisateur.getAnnonces()).doesNotContain(annonceBack);
        assertThat(annonceBack.getUtilisateur()).isNull();
    }
}
