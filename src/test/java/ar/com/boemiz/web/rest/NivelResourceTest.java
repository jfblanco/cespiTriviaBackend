package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.Nivel;
import ar.com.boemiz.repository.NivelRepository;
import ar.com.boemiz.web.rest.mapper.NivelMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NivelResource REST controller.
 *
 * @see NivelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NivelResourceTest {


    private static final Integer DEFAULT_NUMERO = 0;
    private static final Integer UPDATED_NUMERO = 1;

    @Inject
    private NivelRepository nivelRepository;

    @Inject
    private NivelMapper nivelMapper;

    private MockMvc restNivelMockMvc;

    private Nivel nivel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NivelResource nivelResource = new NivelResource();
        ReflectionTestUtils.setField(nivelResource, "nivelRepository", nivelRepository);
        ReflectionTestUtils.setField(nivelResource, "nivelMapper", nivelMapper);
        this.restNivelMockMvc = MockMvcBuilders.standaloneSetup(nivelResource).build();
    }

    @Before
    public void initTest() {
        nivel = new Nivel();
        nivel.setCodigo(DEFAULT_NUMERO);
    }

    @Test
    @Ignore
    @Transactional
    public void createNivel() throws Exception {
        int databaseSizeBeforeCreate = nivelRepository.findAll().size();

        // Create the Nivel
        restNivelMockMvc.perform(post("/api/nivels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nivel)))
                .andExpect(status().isCreated());

        // Validate the Nivel in the database
        List<Nivel> nivels = nivelRepository.findAll();
        assertThat(nivels).hasSize(databaseSizeBeforeCreate + 1);
        Nivel testNivel = nivels.get(nivels.size() - 1);
        assertThat(testNivel.getCodigo()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Ignore
    @Transactional
    public void getAllNivels() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get all the nivels
        restNivelMockMvc.perform(get("/api/nivels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nivel.getId().intValue())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Ignore
    @Transactional
    public void getNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get the nivel
        restNivelMockMvc.perform(get("/api/nivels/{id}", nivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(nivel.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingNivel() throws Exception {
        // Get the nivel
        restNivelMockMvc.perform(get("/api/nivels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Ignore
    @Transactional
    public void updateNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

		int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel
        nivel.setCodigo(UPDATED_NUMERO);
        restNivelMockMvc.perform(put("/api/nivels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nivel)))
                .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivels = nivelRepository.findAll();
        assertThat(nivels).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivels.get(nivels.size() - 1);
        assertThat(testNivel.getCodigo()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Ignore
    @Transactional
    public void deleteNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

		int databaseSizeBeforeDelete = nivelRepository.findAll().size();

        // Get the nivel
        restNivelMockMvc.perform(delete("/api/nivels/{id}", nivel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Nivel> nivels = nivelRepository.findAll();
        assertThat(nivels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
