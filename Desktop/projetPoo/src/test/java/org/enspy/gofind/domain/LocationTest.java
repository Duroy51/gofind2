package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceTestSamples.*;
import static org.enspy.gofind.domain.LocationTestSamples.*;

import org.enspy.gofind.web.rest.TestUtil;
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
    void annonceTest() {
        Location location = getLocationRandomSampleGenerator();
        Annonce annonceBack = getAnnonceRandomSampleGenerator();

        location.setAnnonce(annonceBack);
        assertThat(location.getAnnonce()).isEqualTo(annonceBack);
        assertThat(annonceBack.getLocation()).isEqualTo(location);

        location.annonce(null);
        assertThat(location.getAnnonce()).isNull();
        assertThat(annonceBack.getLocation()).isNull();
    }
}
