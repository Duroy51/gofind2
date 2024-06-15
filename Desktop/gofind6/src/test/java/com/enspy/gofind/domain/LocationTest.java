package com.enspy.gofind.domain;

import static com.enspy.gofind.domain.LocationTestSamples.*;
import static com.enspy.gofind.domain.UtilisateurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.enspy.gofind.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void utilisateurTest() {
        Location location = getLocationRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        location.setUtilisateur(utilisateurBack);
        assertThat(location.getUtilisateur()).isEqualTo(utilisateurBack);

        location.utilisateur(null);
        assertThat(location.getUtilisateur()).isNull();
    }
}
