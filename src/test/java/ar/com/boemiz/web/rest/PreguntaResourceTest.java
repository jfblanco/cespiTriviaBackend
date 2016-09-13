package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.Pregunta;
import ar.com.boemiz.repository.PreguntaRepository;
import ar.com.boemiz.web.rest.mapper.PreguntaMapper;

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
 * Test class for the PreguntaResource REST controller.
 *
 * @see PreguntaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PreguntaResourceTest {

    private static final String DEFAULT_DESCRIPCION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPCION = "UPDATED_TEXT";
    private static final String DEFAULT_OPCION1 = "SAMPLE_TEXT";
    private static final String UPDATED_OPCION1 = "UPDATED_TEXT";
    private static final String DEFAULT_OPCION2 = "SAMPLE_TEXT";
    private static final String UPDATED_OPCION2 = "UPDATED_TEXT";
    private static final String DEFAULT_OPCION3 = "SAMPLE_TEXT";
    private static final String UPDATED_OPCION3 = "UPDATED_TEXT";
    private static final String DEFAULT_OPCION4 = "SAMPLE_TEXT";
    private static final String UPDATED_OPCION4 = "UPDATED_TEXT";
    private static final String DEFAULT_CORRECTA = "SAMPLE_TEXT";
    private static final String UPDATED_CORRECTA = "UPDATED_TEXT";

    @Inject
    private PreguntaRepository preguntaRepository;

    @Inject
    private PreguntaMapper preguntaMapper;

    private MockMvc restPreguntaMockMvc;

    private Pregunta pregunta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreguntaResource preguntaResource = new PreguntaResource();
        ReflectionTestUtils.setField(preguntaResource, "preguntaRepository", preguntaRepository);
        ReflectionTestUtils.setField(preguntaResource, "preguntaMapper", preguntaMapper);
        this.restPreguntaMockMvc = MockMvcBuilders.standaloneSetup(preguntaResource).build();
    }

    @Before
    public void initTest() {
        pregunta = new Pregunta();
        pregunta.setDescripcion(DEFAULT_DESCRIPCION);
        pregunta.setOpcion1(DEFAULT_OPCION1);
        pregunta.setOpcion2(DEFAULT_OPCION2);
        pregunta.setOpcion3(DEFAULT_OPCION3);
        pregunta.setCorrecta(DEFAULT_CORRECTA);
    }

    @Test
    @Transactional
    public void createPregunta() throws Exception {
        int databaseSizeBeforeCreate = preguntaRepository.findAll().size();

        // Create the Pregunta
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isCreated());

        // Validate the Pregunta in the database
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeCreate + 1);
        Pregunta testPregunta = preguntas.get(preguntas.size() - 1);
        assertThat(testPregunta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPregunta.getOpcion1()).isEqualTo(DEFAULT_OPCION1);
        assertThat(testPregunta.getOpcion2()).isEqualTo(DEFAULT_OPCION2);
        assertThat(testPregunta.getOpcion3()).isEqualTo(DEFAULT_OPCION3);
        assertThat(testPregunta.getCorrecta()).isEqualTo(DEFAULT_CORRECTA);
    }

    @Test
    @Ignore
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);
        // set the field null
        pregunta.setDescripcion(null);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void checkOpcion1IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);
        // set the field null
        pregunta.setOpcion1(null);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void checkOpcion2IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);
        // set the field null
        pregunta.setOpcion2(null);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void checkOpcion3IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);
        // set the field null
        pregunta.setOpcion3(null);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void checkOpcion4IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void checkCorrectaIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(preguntaRepository.findAll()).hasSize(0);
        // set the field null
        pregunta.setCorrecta(null);

        // Create the Pregunta, which fails.
        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(0);
    }

    @Test
    @Ignore
    @Transactional
    public void getAllPreguntas() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

        // Get all the preguntas
        restPreguntaMockMvc.perform(get("/api/preguntas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pregunta.getId().intValue())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].opcion1").value(hasItem(DEFAULT_OPCION1.toString())))
                .andExpect(jsonPath("$.[*].opcion2").value(hasItem(DEFAULT_OPCION2.toString())))
                .andExpect(jsonPath("$.[*].opcion3").value(hasItem(DEFAULT_OPCION3.toString())))
                .andExpect(jsonPath("$.[*].opcion4").value(hasItem(DEFAULT_OPCION4.toString())))
                .andExpect(jsonPath("$.[*].correcta").value(hasItem(DEFAULT_CORRECTA.toString())));
    }

    @Test
    @Ignore
    @Transactional
    public void getPregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

        // Get the pregunta
        restPreguntaMockMvc.perform(get("/api/preguntas/{id}", pregunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pregunta.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.opcion1").value(DEFAULT_OPCION1.toString()))
            .andExpect(jsonPath("$.opcion2").value(DEFAULT_OPCION2.toString()))
            .andExpect(jsonPath("$.opcion3").value(DEFAULT_OPCION3.toString()))
            .andExpect(jsonPath("$.opcion4").value(DEFAULT_OPCION4.toString()))
            .andExpect(jsonPath("$.correcta").value(DEFAULT_CORRECTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPregunta() throws Exception {
        // Get the pregunta
        restPreguntaMockMvc.perform(get("/api/preguntas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

		int databaseSizeBeforeUpdate = preguntaRepository.findAll().size();

        // Update the pregunta
        pregunta.setDescripcion(UPDATED_DESCRIPCION);
        pregunta.setOpcion1(UPDATED_OPCION1);
        pregunta.setOpcion2(UPDATED_OPCION2);
        pregunta.setOpcion3(UPDATED_OPCION3);
        pregunta.setCorrecta(UPDATED_CORRECTA);
        restPreguntaMockMvc.perform(put("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isOk());

        // Validate the Pregunta in the database
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeUpdate);
        Pregunta testPregunta = preguntas.get(preguntas.size() - 1);
        assertThat(testPregunta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPregunta.getOpcion1()).isEqualTo(UPDATED_OPCION1);
        assertThat(testPregunta.getOpcion2()).isEqualTo(UPDATED_OPCION2);
        assertThat(testPregunta.getOpcion3()).isEqualTo(UPDATED_OPCION3);
        assertThat(testPregunta.getCorrecta()).isEqualTo(UPDATED_CORRECTA);
    }

    @Test
    @Transactional
    public void deletePregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

		int databaseSizeBeforeDelete = preguntaRepository.findAll().size();

        // Get the pregunta
        restPreguntaMockMvc.perform(delete("/api/preguntas/{id}", pregunta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
