package com.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilisateurAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUtilisateurAllPropertiesEquals(Utilisateur expected, Utilisateur actual) {
        assertUtilisateurAutoGeneratedPropertiesEquals(expected, actual);
        assertUtilisateurAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUtilisateurAllUpdatablePropertiesEquals(Utilisateur expected, Utilisateur actual) {
        assertUtilisateurUpdatableFieldsEquals(expected, actual);
        assertUtilisateurUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUtilisateurAutoGeneratedPropertiesEquals(Utilisateur expected, Utilisateur actual) {
        assertThat(expected)
            .as("Verify Utilisateur auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUtilisateurUpdatableFieldsEquals(Utilisateur expected, Utilisateur actual) {
        assertThat(expected)
            .as("Verify Utilisateur relevant properties")
            .satisfies(e -> assertThat(e.getUserName()).as("check userName").isEqualTo(actual.getUserName()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertUtilisateurUpdatableRelationshipsEquals(Utilisateur expected, Utilisateur actual) {}
}
