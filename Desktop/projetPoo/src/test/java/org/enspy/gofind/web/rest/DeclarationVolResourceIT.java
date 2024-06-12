package org.enspy.gofind.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.enspy.gofind.domain.DeclarationVolAsserts.*;
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
import org.enspy.gofind.domain.DeclarationVol;
import org.enspy.gofind.repository.DeclarationVolRepository;
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
 * Integration tests for the {@link DeclarationVolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeclarationVolResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MODELE = "AAAAAAAAAA";
    private static final String UPDATED_MODELE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMEROSERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROSERIE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATEVOL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEVOL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/declaration-vols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeclarationVolRepository declarationVolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeclarationVolMockMvc;

    private DeclarationVol declarationVol;

    private DeclarationVol insertedDeclarationVol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeclarationVol createEntity(EntityManager em) {
        DeclarationVol declarationVol = new DeclarationVol()
            .type(DEFAULT_TYPE)
            .marque(DEFAULT_MARQUE)
            .modele(DEFAULT_MODELE)
            .numeroserie(DEFAULT_NUMEROSERIE)
            .datevol(DEFAULT_DATEVOL);
        return declarationVol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeclarationVol createUpdatedEntity(EntityManager em) {
        DeclarationVol declarationVol = new DeclarationVol()
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroserie(UPDATED_NUMEROSERIE)
            .datevol(UPDATED_DATEVOL);
        return declarationVol;
    }

    @BeforeEach
    public void initTest() {
        declarationVol = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDeclarationVol != null) {
            declarationVolRepository.delete(insertedDeclarationVol);
            insertedDeclarationVol = null;
        }
    }

    @Test
    @Transactional
    void createDeclarationVol() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DeclarationVol
        var returnedDeclarationVol = om.readValue(
            restDeclarationVolMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declarationVol)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DeclarationVol.class
        );

        // Validate the DeclarationVol in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDeclarationVolUpdatableFieldsEquals(returnedDeclarationVol, getPersistedDeclarationVol(returnedDeclarationVol));

        insertedDeclarationVol = returnedDeclarationVol;
    }

    @Test
    @Transactional
    void createDeclarationVolWithExistingId() throws Exception {
        // Create the DeclarationVol with an existing ID
        declarationVol.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclarationVolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declarationVol)))
            .andExpect(status().isBadRequest());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeclarationVols() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        // Get all the declarationVolList
        restDeclarationVolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declarationVol.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].modele").value(hasItem(DEFAULT_MODELE)))
            .andExpect(jsonPath("$.[*].numeroserie").value(hasItem(DEFAULT_NUMEROSERIE)))
            .andExpect(jsonPath("$.[*].datevol").value(hasItem(DEFAULT_DATEVOL.toString())));
    }

    @Test
    @Transactional
    void getDeclarationVol() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        // Get the declarationVol
        restDeclarationVolMockMvc
            .perform(get(ENTITY_API_URL_ID, declarationVol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(declarationVol.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.modele").value(DEFAULT_MODELE))
            .andExpect(jsonPath("$.numeroserie").value(DEFAULT_NUMEROSERIE))
            .andExpect(jsonPath("$.datevol").value(DEFAULT_DATEVOL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeclarationVol() throws Exception {
        // Get the declarationVol
        restDeclarationVolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeclarationVol() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declarationVol
        DeclarationVol updatedDeclarationVol = declarationVolRepository.findById(declarationVol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeclarationVol are not directly saved in db
        em.detach(updatedDeclarationVol);
        updatedDeclarationVol
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroserie(UPDATED_NUMEROSERIE)
            .datevol(UPDATED_DATEVOL);

        restDeclarationVolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeclarationVol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDeclarationVol))
            )
            .andExpect(status().isOk());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeclarationVolToMatchAllProperties(updatedDeclarationVol);
    }

    @Test
    @Transactional
    void putNonExistingDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declarationVol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declarationVol))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(declarationVol))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(declarationVol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeclarationVolWithPatch() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declarationVol using partial update
        DeclarationVol partialUpdatedDeclarationVol = new DeclarationVol();
        partialUpdatedDeclarationVol.setId(declarationVol.getId());

        partialUpdatedDeclarationVol.type(UPDATED_TYPE).marque(UPDATED_MARQUE).datevol(UPDATED_DATEVOL);

        restDeclarationVolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclarationVol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclarationVol))
            )
            .andExpect(status().isOk());

        // Validate the DeclarationVol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclarationVolUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeclarationVol, declarationVol),
            getPersistedDeclarationVol(declarationVol)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeclarationVolWithPatch() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the declarationVol using partial update
        DeclarationVol partialUpdatedDeclarationVol = new DeclarationVol();
        partialUpdatedDeclarationVol.setId(declarationVol.getId());

        partialUpdatedDeclarationVol
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .modele(UPDATED_MODELE)
            .numeroserie(UPDATED_NUMEROSERIE)
            .datevol(UPDATED_DATEVOL);

        restDeclarationVolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclarationVol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeclarationVol))
            )
            .andExpect(status().isOk());

        // Validate the DeclarationVol in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeclarationVolUpdatableFieldsEquals(partialUpdatedDeclarationVol, getPersistedDeclarationVol(partialUpdatedDeclarationVol));
    }

    @Test
    @Transactional
    void patchNonExistingDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, declarationVol.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declarationVol))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(declarationVol))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeclarationVol() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        declarationVol.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclarationVolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(declarationVol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeclarationVol in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeclarationVol() throws Exception {
        // Initialize the database
        insertedDeclarationVol = declarationVolRepository.saveAndFlush(declarationVol);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the declarationVol
        restDeclarationVolMockMvc
            .perform(delete(ENTITY_API_URL_ID, declarationVol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return declarationVolRepository.count();
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

    protected DeclarationVol getPersistedDeclarationVol(DeclarationVol declarationVol) {
        return declarationVolRepository.findById(declarationVol.getId()).orElseThrow();
    }

    protected void assertPersistedDeclarationVolToMatchAllProperties(DeclarationVol expectedDeclarationVol) {
        assertDeclarationVolAllPropertiesEquals(expectedDeclarationVol, getPersistedDeclarationVol(expectedDeclarationVol));
    }

    protected void assertPersistedDeclarationVolToMatchUpdatableProperties(DeclarationVol expectedDeclarationVol) {
        assertDeclarationVolAllUpdatablePropertiesEquals(expectedDeclarationVol, getPersistedDeclarationVol(expectedDeclarationVol));
    }
}
