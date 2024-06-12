package org.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnnonceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Annonce getAnnonceSample1() {
        return new Annonce().id(1L).titre("titre1").description("description1").type("type1").statut("statut1");
    }

    public static Annonce getAnnonceSample2() {
        return new Annonce().id(2L).titre("titre2").description("description2").type("type2").statut("statut2");
    }

    public static Annonce getAnnonceRandomSampleGenerator() {
        return new Annonce()
            .id(longCount.incrementAndGet())
            .titre(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .statut(UUID.randomUUID().toString());
    }
}
