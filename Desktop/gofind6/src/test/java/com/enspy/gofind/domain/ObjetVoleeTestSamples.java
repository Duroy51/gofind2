package com.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObjetVoleeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ObjetVolee getObjetVoleeSample1() {
        return new ObjetVolee().id(1L).type("type1").marque("marque1").numeroSerie("numeroSerie1");
    }

    public static ObjetVolee getObjetVoleeSample2() {
        return new ObjetVolee().id(2L).type("type2").marque("marque2").numeroSerie("numeroSerie2");
    }

    public static ObjetVolee getObjetVoleeRandomSampleGenerator() {
        return new ObjetVolee()
            .id(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .marque(UUID.randomUUID().toString())
            .numeroSerie(UUID.randomUUID().toString());
    }
}
