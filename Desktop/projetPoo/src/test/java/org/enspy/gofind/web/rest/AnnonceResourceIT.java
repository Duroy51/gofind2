package org.enspy.gofind.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.AnnonceAsserts.*;
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
import org.enspy.gofind.domain.Annonce;
import org.enspy.gofind.repository.AnnonceRepository;
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
 * Integration tests for the {@link AnnonceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnonceResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATECREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATECREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/annonces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnonceMockMvc;

    private Annonce annonce;

    private Annonce insertedAnnonce;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annonce createEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .titre(DEFAULT_TITRE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .statut(DEFAULT_STATUT)
            .datecreation(DEFAULT_DATECREATION);
        return annonce;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annonce createUpdatedEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .statut(UPDATED_STATUT)
            .datecreation(UPDATED_DATECREATION);
        return annonce;
    }

    @BeforeEach
    public void initTest() {
        annonce = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnnonce != null) {
            annonceRepository.delete(insertedAnnonce);
            insertedAnnonce = null;
        }
    }

    @Test
    @Transactional
    void createAnnonce() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Annonce
        var returnedAnnonce = om.readValue(
            restAnnonceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(annonce)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Annonce.class
        );

        // Validate the Annonce in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnnonceUpdatableFieldsEquals(returnedAnnonce, getPersistedAnnonce(returnedAnnonce));

        insertedAnnonce = returnedAnnonce;
    }

    @Test
    @Transactional
    void createAnnonceWithExistingId() throws Exception {
        // Create the Annonce with an existing ID
        annonce.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(annonce)))
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnnonces() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        // Get all the annonceList
        restAnnonceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].datecreation").value(hasItem(DEFAULT_DATECREATION.toString())));
    }

    @Test
    @Transactional
    void getAnnonce() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        // Get the annonce
        restAnnonceMockMvc
            .perform(get(ENTITY_API_URL_ID, annonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(annonce.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.datecreation").value(DEFAULT_DATECREATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnnonce() throws Exception {
        // Get the annonce
        restAnnonceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnnonce() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the annonce
        Annonce updatedAnnonce = annonceRepository.findById(annonce.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnnonce are not directly saved in db
        em.detach(updatedAnnonce);
        updatedAnnonce
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .statut(UPDATED_STATUT)
            .datecreation(UPDATED_DATECREATION);

        restAnnonceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnnonce.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnnonce))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnnonceToMatchAllProperties(updatedAnnonce);
    }

    @Test
    @Transactional
    void putNonExistingAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(put(ENTITY_API_URL_ID, annonce.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(annonce)))
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(annonce))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(annonce)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnonceWithPatch() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the annonce using partial update
        Annonce partialUpdatedAnnonce = new Annonce();
        partialUpdatedAnnonce.setId(annonce.getId());

        partialUpdatedAnnonce.titre(UPDATED_TITRE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).statut(UPDATED_STATUT);

        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnonce.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnnonce))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnnonceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAnnonce, annonce), getPersistedAnnonce(annonce));
    }

    @Test
    @Transactional
    void fullUpdateAnnonceWithPatch() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the annonce using partial update
        Annonce partialUpdatedAnnonce = new Annonce();
        partialUpdatedAnnonce.setId(annonce.getId());

        partialUpdatedAnnonce
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .statut(UPDATED_STATUT)
            .datecreation(UPDATED_DATECREATION);

        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnonce.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnnonce))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnnonceUpdatableFieldsEquals(partialUpdatedAnnonce, getPersistedAnnonce(partialUpdatedAnnonce));
    }

    @Test
    @Transactional
    void patchNonExistingAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, annonce.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(annonce))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(annonce))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnonce() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        annonce.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(annonce)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annonce in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnonce() throws Exception {
        // Initialize the database
        insertedAnnonce = annonceRepository.saveAndFlush(annonce);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the annonce
        restAnnonceMockMvc
            .perform(delete(ENTITY_API_URL_ID, annonce.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return annonceRepository.count();
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

    protected Annonce getPersistedAnnonce(Annonce annonce) {
        return annonceRepository.findById(annonce.getId()).orElseThrow();
    }

    protected void assertPersistedAnnonceToMatchAllProperties(Annonce expectedAnnonce) {
        assertAnnonceAllPropertiesEquals(expectedAnnonce, getPersistedAnnonce(expectedAnnonce));
    }

    protected void assertPersistedAnnonceToMatchUpdatableProperties(Annonce expectedAnnonce) {
        assertAnnonceAllUpdatablePropertiesEquals(expectedAnnonce, getPersistedAnnonce(expectedAnnonce));
    }
}
