package org.enspy.gofind.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UtilisateurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Utilisateur getUtilisateurSample1() {
        return new Utilisateur().id(1L).nom("nom1").prenom("prenom1").email("email1").telephone("telephone1").password("password1");
    }

    public static Utilisateur getUtilisateurSample2() {
        return new Utilisateur().id(2L).nom("nom2").prenom("prenom2").email("email2").telephone("telephone2").password("password2");
    }

    public static Utilisateur getUtilisateurRandomSampleGenerator() {
        return new Utilisateur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}