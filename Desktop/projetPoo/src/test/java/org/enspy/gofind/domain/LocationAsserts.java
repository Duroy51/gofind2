package org.enspy.gofind.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAllPropertiesEquals(Location expected, Location actual) {
        assertLocationAutoGeneratedPropertiesEquals(expected, actual);
        assertLocationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAllUpdatablePropertiesEquals(Location expected, Location actual) {
        assertLocationUpdatableFieldsEquals(expected, actual);
        assertLocationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAutoGeneratedPropertiesEquals(Location expected, Location actual) {
        assertThat(expected)
            .as("Verify Location auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationUpdatableFieldsEquals(Location expected, Location actual) {
        assertThat(expected)
            .as("Verify Location relevant properties")
            .satisfies(e -> assertThat(e.getDatedebut()).as("check datedebut").isEqualTo(actual.getDatedebut()))
            .satisfies(e -> assertThat(e.getDatefin()).as("check datefin").isEqualTo(actual.getDatefin()))
            .satisfies(e -> assertThat(e.getPrix()).as("check prix").isEqualTo(actual.getPrix()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationUpdatableRelationshipsEquals(Location expected, Location actual) {}
}
