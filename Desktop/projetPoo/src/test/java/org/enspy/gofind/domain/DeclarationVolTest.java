package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceTestSamples.*;
import static org.enspy.gofind.domain.DeclarationVolTestSamples.*;

import org.enspy.gofind.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclarationVolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeclarationVol.class);
        DeclarationVol declarationVol1 = getDeclarationVolSample1();
        DeclarationVol declarationVol2 = new DeclarationVol();
        assertThat(declarationVol1).isNotEqualTo(declarationVol2);

        declarationVol2.setId(declarationVol1.getId());
        assertThat(declarationVol1).isEqualTo(declarationVol2);

        declarationVol2 = getDeclarationVolSample2();
        assertThat(declarationVol1).isNotEqualTo(declarationVol2);
    }

    @Test
    void annonceTest() {
        DeclarationVol declarationVol = getDeclarationVolRandomSampleGenerator();
        Annonce annonceBack = getAnnonceRandomSampleGenerator();

        declarationVol.setAnnonce(annonceBack);
        assertThat(declarationVol.getAnnonce()).isEqualTo(annonceBack);
        assertThat(annonceBack.getDeclarationVol()).isEqualTo(declarationVol);

        declarationVol.annonce(null);
        assertThat(declarationVol.getAnnonce()).isNull();
        assertThat(annonceBack.getDeclarationVol()).isNull();
    }
}
