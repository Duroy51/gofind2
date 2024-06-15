package com.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UtilisateurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Utilisateur getUtilisateurSample1() {
        return new Utilisateur().id(1L).userName("userName1").email("email1");
    }

    public static Utilisateur getUtilisateurSample2() {
        return new Utilisateur().id(2L).userName("userName2").email("email2");
    }

    public static Utilisateur getUtilisateurRandomSampleGenerator() {
        return new Utilisateur().id(longCount.incrementAndGet()).userName(UUID.randomUUID().toString()).email(UUID.randomUUID().toString());
    }
}
