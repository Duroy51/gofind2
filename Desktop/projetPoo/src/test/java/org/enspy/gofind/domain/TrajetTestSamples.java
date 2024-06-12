package org.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TrajetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Trajet getTrajetSample1() {
        return new Trajet().id(1L).lieudepart("lieudepart1").lieuarrivee("lieuarrivee1").nombreplace(1).marqueVehicule("marqueVehicule1");
    }

    public static Trajet getTrajetSample2() {
        return new Trajet().id(2L).lieudepart("lieudepart2").lieuarrivee("lieuarrivee2").nombreplace(2).marqueVehicule("marqueVehicule2");
    }

    public static Trajet getTrajetRandomSampleGenerator() {
        return new Trajet()
            .id(longCount.incrementAndGet())
            .lieudepart(UUID.randomUUID().toString())
            .lieuarrivee(UUID.randomUUID().toString())
            .nombreplace(intCount.incrementAndGet())
            .marqueVehicule(UUID.randomUUID().toString());
    }
}
