package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.Dificultad;
import ar.com.boemiz.repository.DificultadRepository;
import ar.com.boemiz.web.rest.mapper.DificultadMapper;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DificultadResource REST controller.
 *
 * @see DificultadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DificultadResourceTest {

    private static final Integer DEFAULT_CODIGO = 12;
    private static final Integer UPDATED_CODIGO = 15;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;
    private static final String DEFAULT_DESCRIPCION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPCION = "UPDATED_TEXT";

    @Inject
    private DificultadRepository dificultadRepository;

    @Inject
    private DificultadMapper dificultadMapper;

    private MockMvc restDificultadMockMvc;

    private Dificultad dificultad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DificultadResource dificultadResource = new DificultadResource();
        ReflectionTestUtils.setField(dificultadResource, "dificultadRepository", dificultadRepository);
        ReflectionTestUtils.setField(dificultadResource, "dificultadMapper", dificultadMapper);
        this.restDificultadMockMvc = MockMvcBuilders.standaloneSetup(dificultadResource).build();
    }

    @Before
    public void initTest() {
        dificultad = new Dificultad();
        dificultad.setCodigo(DEFAULT_CODIGO);
        dificultad.setEstado(DEFAULT_ESTADO);
        dificultad.setDescripcion(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createDificultad() throws Exception {
        int databaseSizeBeforeCreate = dificultadRepository.findAll().size();

        // Create the Dificultad
        restDificultadMockMvc.perform(post("/api/dificultads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dificultad)))
                .andExpect(status().isCreated());

        // Validate the Dificultad in the database
        List<Dificultad> dificultads = dificultadRepository.findAll();
        assertThat(dificultads).hasSize(databaseSizeBeforeCreate + 1);
        Dificultad testDificultad = dificultads.get(dificultads.size() - 1);
        assertThat(testDificultad.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testDificultad.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testDificultad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(dificultadRepository.findAll()).hasSize(0);
        // set the field null
        dificultad.setCodigo(null);

        // Create the Dificultad, which fails.
        restDificultadMockMvc.perform(post("/api/dificultads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dificultad)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Dificultad> dificultads = dificultadRepository.findAll();
        assertThat(dificultads).hasSize(0);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(dificultadRepository.findAll()).hasSize(0);
        // set the field null
        dificultad.setDescripcion(null);

        // Create the Dificultad, which fails.
        restDificultadMockMvc.perform(post("/api/dificultads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dificultad)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Dificultad> dificultads = dificultadRepository.findAll();
        assertThat(dificultads).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllDificultads() throws Exception {
        // Initialize the database
        dificultadRepository.saveAndFlush(dificultad);

        // Get all the dificultads
        restDificultadMockMvc.perform(get("/api/dificultads"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dificultad.getId().intValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
                .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getDificultad() throws Exception {
        // Initialize the database
        dificultadRepository.saveAndFlush(dificultad);

        // Get the dificultad
        restDificultadMockMvc.perform(get("/api/dificultads/{id}", dificultad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dificultad.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDificultad() throws Exception {
        // Get the dificultad
        restDificultadMockMvc.perform(get("/api/dificultads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDificultad() throws Exception {
        // Initialize the database
        dificultadRepository.saveAndFlush(dificultad);

		int databaseSizeBeforeUpdate = dificultadRepository.findAll().size();

        // Update the dificultad
        dificultad.setCodigo(UPDATED_CODIGO);
        dificultad.setEstado(UPDATED_ESTADO);
        dificultad.setDescripcion(UPDATED_DESCRIPCION);
        restDificultadMockMvc.perform(put("/api/dificultads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dificultad)))
                .andExpect(status().isOk());

        // Validate the Dificultad in the database
        List<Dificultad> dificultads = dificultadRepository.findAll();
        assertThat(dificultads).hasSize(databaseSizeBeforeUpdate);
        Dificultad testDificultad = dificultads.get(dificultads.size() - 1);
        assertThat(testDificultad.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testDificultad.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDificultad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void deleteDificultad() throws Exception {
        // Initialize the database
        dificultadRepository.saveAndFlush(dificultad);

		int databaseSizeBeforeDelete = dificultadRepository.findAll().size();

        // Get the dificultad
        restDificultadMockMvc.perform(delete("/api/dificultads/{id}", dificultad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dificultad> dificultads = dificultadRepository.findAll();
        assertThat(dificultads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
