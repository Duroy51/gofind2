package org.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DeclarationVolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DeclarationVol getDeclarationVolSample1() {
        return new DeclarationVol().id(1L).type("type1").marque("marque1").modele("modele1").numeroserie("numeroserie1");
    }

    public static DeclarationVol getDeclarationVolSample2() {
        return new DeclarationVol().id(2L).type("type2").marque("marque2").modele("modele2").numeroserie("numeroserie2");
    }

    public static DeclarationVol getDeclarationVolRandomSampleGenerator() {
        return new DeclarationVol()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .marque(UUID.randomUUID().toString())
            .modele(UUID.randomUUID().toString())
            .numeroserie(UUID.randomUUID().toString());
    }
}
