package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.Partida;
import ar.com.boemiz.repository.PartidaRepository;
import ar.com.boemiz.web.rest.mapper.PartidaMapper;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartidaResource REST controller.
 *
 * @see PartidaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartidaResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_FECHA = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FECHA = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FECHA_STR = dateTimeFormatter.print(DEFAULT_FECHA);

    @Inject
    private PartidaRepository partidaRepository;

    @Inject
    private PartidaMapper partidaMapper;

    private MockMvc restPartidaMockMvc;

    private Partida partida;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartidaResource partidaResource = new PartidaResource();
        ReflectionTestUtils.setField(partidaResource, "partidaRepository", partidaRepository);
        ReflectionTestUtils.setField(partidaResource, "partidaMapper", partidaMapper);
        this.restPartidaMockMvc = MockMvcBuilders.standaloneSetup(partidaResource).build();
    }

    @Before
    public void initTest() {
        partida = new Partida();
        partida.setFecha(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createPartida() throws Exception {
        int databaseSizeBeforeCreate = partidaRepository.findAll().size();

        // Create the Partida
        restPartidaMockMvc.perform(post("/api/partidas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partida)))
                .andExpect(status().isCreated());

        // Validate the Partida in the database
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeCreate + 1);
        Partida testPartida = partidas.get(partidas.size() - 1);
        assertThat(testPartida.getFecha().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Ignore
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(partidaRepository.findAll()).hasSize(0);
        // set the field null
        partida.setFecha(null);

        // Create the Partida, which fails.
        restPartidaMockMvc.perform(post("/api/partidas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partida)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPartidas() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get all the partidas
        restPartidaMockMvc.perform(get("/api/partidas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partida.getId().intValue())))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA_STR)));
    }

    @Test
    @Transactional
    public void getPartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", partida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partida.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPartida() throws Exception {
        // Get the partida
        restPartidaMockMvc.perform(get("/api/partidas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

		int databaseSizeBeforeUpdate = partidaRepository.findAll().size();

        // Update the partida
        partida.setFecha(UPDATED_FECHA);
        restPartidaMockMvc.perform(put("/api/partidas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partida)))
                .andExpect(status().isOk());

        // Validate the Partida in the database
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeUpdate);
        Partida testPartida = partidas.get(partidas.size() - 1);
        assertThat(testPartida.getFecha().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void deletePartida() throws Exception {
        // Initialize the database
        partidaRepository.saveAndFlush(partida);

		int databaseSizeBeforeDelete = partidaRepository.findAll().size();

        // Get the partida
        restPartidaMockMvc.perform(delete("/api/partidas/{id}", partida.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partida> partidas = partidaRepository.findAll();
        assertThat(partidas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
