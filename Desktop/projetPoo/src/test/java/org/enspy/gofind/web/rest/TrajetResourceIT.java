package org.enspy.gofind.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.TrajetAsserts.*;
import static org.enspy.gofind.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.enspy.gofind.IntegrationTest;
import org.enspy.gofind.domain.Trajet;
import org.enspy.gofind.repository.TrajetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TrajetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrajetResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HEUREDEPART = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEUREDEPART = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HEUREARRIVEE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HEUREARRIVEE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEUDEPART = "AAAAAAAAAA";
    private static final String UPDATED_LIEUDEPART = "BBBBBBBBBB";

    private static final String DEFAULT_LIEUARRIVEE = "AAAAAAAAAA";
    private static final String UPDATED_LIEUARRIVEE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBREPLACE = 1;
    private static final Integer UPDATED_NOMBREPLACE = 2;

    private static final Double DEFAULT_PRIXPLACE = 1D;
    private static final Double UPDATED_PRIXPLACE = 2D;

    private static final String DEFAULT_MARQUE_VEHICULE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE_VEHICULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trajets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrajetMockMvc;

    private Trajet trajet;

    private Trajet insertedTrajet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajet createEntity(EntityManager em) {
        Trajet trajet = new Trajet()
            .date(DEFAULT_DATE)
            .heuredepart(DEFAULT_HEUREDEPART)
            .heurearrivee(DEFAULT_HEUREARRIVEE)
            .lieudepart(DEFAULT_LIEUDEPART)
            .lieuarrivee(DEFAULT_LIEUARRIVEE)
            .nombreplace(DEFAULT_NOMBREPLACE)
            .prixplace(DEFAULT_PRIXPLACE)
            .marqueVehicule(DEFAULT_MARQUE_VEHICULE);
        return trajet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajet createUpdatedEntity(EntityManager em) {
        Trajet trajet = new Trajet()
            .date(UPDATED_DATE)
            .heuredepart(UPDATED_HEUREDEPART)
            .heurearrivee(UPDATED_HEUREARRIVEE)
            .lieudepart(UPDATED_LIEUDEPART)
            .lieuarrivee(UPDATED_LIEUARRIVEE)
            .nombreplace(UPDATED_NOMBREPLACE)
            .prixplace(UPDATED_PRIXPLACE)
            .marqueVehicule(UPDATED_MARQUE_VEHICULE);
        return trajet;
    }

    @BeforeEach
    public void initTest() {
        trajet = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTrajet != null) {
            trajetRepository.delete(insertedTrajet);
            insertedTrajet = null;
        }
    }

    @Test
    @Transactional
    void createTrajet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Trajet
        var returnedTrajet = om.readValue(
            restTrajetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajet)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Trajet.class
        );

        // Validate the Trajet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajetUpdatableFieldsEquals(returnedTrajet, getPersistedTrajet(returnedTrajet));

        insertedTrajet = returnedTrajet;
    }

    @Test
    @Transactional
    void createTrajetWithExistingId() throws Exception {
        // Create the Trajet with an existing ID
        trajet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajet)))
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrajets() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList
        restTrajetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajet.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].heuredepart").value(hasItem(DEFAULT_HEUREDEPART.toString())))
            .andExpect(jsonPath("$.[*].heurearrivee").value(hasItem(DEFAULT_HEUREARRIVEE.toString())))
            .andExpect(jsonPath("$.[*].lieudepart").value(hasItem(DEFAULT_LIEUDEPART)))
            .andExpect(jsonPath("$.[*].lieuarrivee").value(hasItem(DEFAULT_LIEUARRIVEE)))
            .andExpect(jsonPath("$.[*].nombreplace").value(hasItem(DEFAULT_NOMBREPLACE)))
            .andExpect(jsonPath("$.[*].prixplace").value(hasItem(DEFAULT_PRIXPLACE.doubleValue())))
            .andExpect(jsonPath("$.[*].marqueVehicule").value(hasItem(DEFAULT_MARQUE_VEHICULE)));
    }

    @Test
    @Transactional
    void getTrajet() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        // Get the trajet
        restTrajetMockMvc
            .perform(get(ENTITY_API_URL_ID, trajet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trajet.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.heuredepart").value(DEFAULT_HEUREDEPART.toString()))
            .andExpect(jsonPath("$.heurearrivee").value(DEFAULT_HEUREARRIVEE.toString()))
            .andExpect(jsonPath("$.lieudepart").value(DEFAULT_LIEUDEPART))
            .andExpect(jsonPath("$.lieuarrivee").value(DEFAULT_LIEUARRIVEE))
            .andExpect(jsonPath("$.nombreplace").value(DEFAULT_NOMBREPLACE))
            .andExpect(jsonPath("$.prixplace").value(DEFAULT_PRIXPLACE.doubleValue()))
            .andExpect(jsonPath("$.marqueVehicule").value(DEFAULT_MARQUE_VEHICULE));
    }

    @Test
    @Transactional
    void getNonExistingTrajet() throws Exception {
        // Get the trajet
        restTrajetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrajet() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajet
        Trajet updatedTrajet = trajetRepository.findById(trajet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrajet are not directly saved in db
        em.detach(updatedTrajet);
        updatedTrajet
            .date(UPDATED_DATE)
            .heuredepart(UPDATED_HEUREDEPART)
            .heurearrivee(UPDATED_HEUREARRIVEE)
            .lieudepart(UPDATED_LIEUDEPART)
            .lieuarrivee(UPDATED_LIEUARRIVEE)
            .nombreplace(UPDATED_NOMBREPLACE)
            .prixplace(UPDATED_PRIXPLACE)
            .marqueVehicule(UPDATED_MARQUE_VEHICULE);

        restTrajetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrajet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTrajet))
            )
            .andExpect(status().isOk());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrajetToMatchAllProperties(updatedTrajet);
    }

    @Test
    @Transactional
    void putNonExistingTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(put(ENTITY_API_URL_ID, trajet.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajet)))
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trajet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trajet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrajetWithPatch() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajet using partial update
        Trajet partialUpdatedTrajet = new Trajet();
        partialUpdatedTrajet.setId(trajet.getId());

        partialUpdatedTrajet
            .date(UPDATED_DATE)
            .heuredepart(UPDATED_HEUREDEPART)
            .lieudepart(UPDATED_LIEUDEPART)
            .nombreplace(UPDATED_NOMBREPLACE);

        restTrajetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajet))
            )
            .andExpect(status().isOk());

        // Validate the Trajet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTrajet, trajet), getPersistedTrajet(trajet));
    }

    @Test
    @Transactional
    void fullUpdateTrajetWithPatch() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trajet using partial update
        Trajet partialUpdatedTrajet = new Trajet();
        partialUpdatedTrajet.setId(trajet.getId());

        partialUpdatedTrajet
            .date(UPDATED_DATE)
            .heuredepart(UPDATED_HEUREDEPART)
            .heurearrivee(UPDATED_HEUREARRIVEE)
            .lieudepart(UPDATED_LIEUDEPART)
            .lieuarrivee(UPDATED_LIEUARRIVEE)
            .nombreplace(UPDATED_NOMBREPLACE)
            .prixplace(UPDATED_PRIXPLACE)
            .marqueVehicule(UPDATED_MARQUE_VEHICULE);

        restTrajetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrajet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrajet))
            )
            .andExpect(status().isOk());

        // Validate the Trajet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrajetUpdatableFieldsEquals(partialUpdatedTrajet, getPersistedTrajet(partialUpdatedTrajet));
    }

    @Test
    @Transactional
    void patchNonExistingTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trajet.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trajet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trajet))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrajet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trajet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrajetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trajet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrajet() throws Exception {
        // Initialize the database
        insertedTrajet = trajetRepository.saveAndFlush(trajet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trajet
        restTrajetMockMvc
            .perform(delete(ENTITY_API_URL_ID, trajet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajetRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Trajet getPersistedTrajet(Trajet trajet) {
        return trajetRepository.findById(trajet.getId()).orElseThrow();
    }

    protected void assertPersistedTrajetToMatchAllProperties(Trajet expectedTrajet) {
        assertTrajetAllPropertiesEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
    }

    protected void assertPersistedTrajetToMatchUpdatableProperties(Trajet expectedTrajet) {
        assertTrajetAllUpdatablePropertiesEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
    }
}
