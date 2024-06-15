package com.enspy.gofind.web.rest;

import static com.enspy.gofind.domain.ObjetVoleeAsserts.*;
import static com.enspy.gofind.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.enspy.gofind.IntegrationTest;
import com.enspy.gofind.domain.ObjetVolee;
import com.enspy.gofind.repository.ObjetVoleeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link ObjetVoleeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ObjetVoleeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEVOL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEVOL = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO_OBJET = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_OBJET = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_OBJET_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_OBJET_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/objet-volees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ObjetVoleeRepository objetVoleeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjetVoleeMockMvc;

    private ObjetVolee objetVolee;

    private ObjetVolee insertedObjetVolee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjetVolee createEntity(EntityManager em) {
        ObjetVolee objetVolee = new ObjetVolee()
            .type(DEFAULT_TYPE)
            .marque(DEFAULT_MARQUE)
            .datevol(DEFAULT_DATEVOL)
            .photoObjet(DEFAULT_PHOTO_OBJET)
            .photoObjetContentType(DEFAULT_PHOTO_OBJET_CONTENT_TYPE)
            .numeroSerie(DEFAULT_NUMERO_SERIE);
        return objetVolee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjetVolee createUpdatedEntity(EntityManager em) {
        ObjetVolee objetVolee = new ObjetVolee()
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .datevol(UPDATED_DATEVOL)
            .photoObjet(UPDATED_PHOTO_OBJET)
            .photoObjetContentType(UPDATED_PHOTO_OBJET_CONTENT_TYPE)
            .numeroSerie(UPDATED_NUMERO_SERIE);
        return objetVolee;
    }

    @BeforeEach
    public void initTest() {
        objetVolee = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedObjetVolee != null) {
            objetVoleeRepository.delete(insertedObjetVolee);
            insertedObjetVolee = null;
        }
    }

    @Test
    @Transactional
    void createObjetVolee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ObjetVolee
        var returnedObjetVolee = om.readValue(
            restObjetVoleeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(objetVolee)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ObjetVolee.class
        );

        // Validate the ObjetVolee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertObjetVoleeUpdatableFieldsEquals(returnedObjetVolee, getPersistedObjetVolee(returnedObjetVolee));

        insertedObjetVolee = returnedObjetVolee;
    }

    @Test
    @Transactional
    void createObjetVoleeWithExistingId() throws Exception {
        // Create the ObjetVolee with an existing ID
        objetVolee.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjetVoleeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(objetVolee)))
            .andExpect(status().isBadRequest());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObjetVolees() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        // Get all the objetVoleeList
        restObjetVoleeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objetVolee.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].datevol").value(hasItem(DEFAULT_DATEVOL.toString())))
            .andExpect(jsonPath("$.[*].photoObjetContentType").value(hasItem(DEFAULT_PHOTO_OBJET_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoObjet").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO_OBJET))))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)));
    }

    @Test
    @Transactional
    void getObjetVolee() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        // Get the objetVolee
        restObjetVoleeMockMvc
            .perform(get(ENTITY_API_URL_ID, objetVolee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objetVolee.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.datevol").value(DEFAULT_DATEVOL.toString()))
            .andExpect(jsonPath("$.photoObjetContentType").value(DEFAULT_PHOTO_OBJET_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoObjet").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO_OBJET)))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE));
    }

    @Test
    @Transactional
    void getNonExistingObjetVolee() throws Exception {
        // Get the objetVolee
        restObjetVoleeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObjetVolee() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objetVolee
        ObjetVolee updatedObjetVolee = objetVoleeRepository.findById(objetVolee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedObjetVolee are not directly saved in db
        em.detach(updatedObjetVolee);
        updatedObjetVolee
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .datevol(UPDATED_DATEVOL)
            .photoObjet(UPDATED_PHOTO_OBJET)
            .photoObjetContentType(UPDATED_PHOTO_OBJET_CONTENT_TYPE)
            .numeroSerie(UPDATED_NUMERO_SERIE);

        restObjetVoleeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedObjetVolee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedObjetVolee))
            )
            .andExpect(status().isOk());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedObjetVoleeToMatchAllProperties(updatedObjetVolee);
    }

    @Test
    @Transactional
    void putNonExistingObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objetVolee.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(objetVolee))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(objetVolee))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(objetVolee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjetVoleeWithPatch() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objetVolee using partial update
        ObjetVolee partialUpdatedObjetVolee = new ObjetVolee();
        partialUpdatedObjetVolee.setId(objetVolee.getId());

        partialUpdatedObjetVolee.type(UPDATED_TYPE).marque(UPDATED_MARQUE);

        restObjetVoleeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjetVolee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedObjetVolee))
            )
            .andExpect(status().isOk());

        // Validate the ObjetVolee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObjetVoleeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedObjetVolee, objetVolee),
            getPersistedObjetVolee(objetVolee)
        );
    }

    @Test
    @Transactional
    void fullUpdateObjetVoleeWithPatch() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objetVolee using partial update
        ObjetVolee partialUpdatedObjetVolee = new ObjetVolee();
        partialUpdatedObjetVolee.setId(objetVolee.getId());

        partialUpdatedObjetVolee
            .type(UPDATED_TYPE)
            .marque(UPDATED_MARQUE)
            .datevol(UPDATED_DATEVOL)
            .photoObjet(UPDATED_PHOTO_OBJET)
            .photoObjetContentType(UPDATED_PHOTO_OBJET_CONTENT_TYPE)
            .numeroSerie(UPDATED_NUMERO_SERIE);

        restObjetVoleeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjetVolee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedObjetVolee))
            )
            .andExpect(status().isOk());

        // Validate the ObjetVolee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObjetVoleeUpdatableFieldsEquals(partialUpdatedObjetVolee, getPersistedObjetVolee(partialUpdatedObjetVolee));
    }

    @Test
    @Transactional
    void patchNonExistingObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objetVolee.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(objetVolee))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(objetVolee))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjetVolee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objetVolee.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjetVoleeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(objetVolee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjetVolee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjetVolee() throws Exception {
        // Initialize the database
        insertedObjetVolee = objetVoleeRepository.saveAndFlush(objetVolee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the objetVolee
        restObjetVoleeMockMvc
            .perform(delete(ENTITY_API_URL_ID, objetVolee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return objetVoleeRepository.count();
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

    protected ObjetVolee getPersistedObjetVolee(ObjetVolee objetVolee) {
        return objetVoleeRepository.findById(objetVolee.getId()).orElseThrow();
    }

    protected void assertPersistedObjetVoleeToMatchAllProperties(ObjetVolee expectedObjetVolee) {
        assertObjetVoleeAllPropertiesEquals(expectedObjetVolee, getPersistedObjetVolee(expectedObjetVolee));
    }

    protected void assertPersistedObjetVoleeToMatchUpdatableProperties(ObjetVolee expectedObjetVolee) {
        assertObjetVoleeAllUpdatablePropertiesEquals(expectedObjetVolee, getPersistedObjetVolee(expectedObjetVolee));
    }
}
