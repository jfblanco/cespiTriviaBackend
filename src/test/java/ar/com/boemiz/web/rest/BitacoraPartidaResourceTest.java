package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.BitacoraPartida;
import ar.com.boemiz.repository.BitacoraPartidaRepository;
import ar.com.boemiz.web.rest.mapper.BitacoraPartidaMapper;

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
 * Test class for the BitacoraPartidaResource REST controller.
 *
 * @see BitacoraPartidaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BitacoraPartidaResourceTest {


    @Inject
    private BitacoraPartidaRepository bitacoraPartidaRepository;

    @Inject
    private BitacoraPartidaMapper bitacoraPartidaMapper;

    private MockMvc restBitacoraPartidaMockMvc;

    private BitacoraPartida bitacoraPartida;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BitacoraPartidaResource bitacoraPartidaResource = new BitacoraPartidaResource();
        ReflectionTestUtils.setField(bitacoraPartidaResource, "bitacoraPartidaRepository", bitacoraPartidaRepository);
        ReflectionTestUtils.setField(bitacoraPartidaResource, "bitacoraPartidaMapper", bitacoraPartidaMapper);
        this.restBitacoraPartidaMockMvc = MockMvcBuilders.standaloneSetup(bitacoraPartidaResource).build();
    }

    @Before
    public void initTest() {
        bitacoraPartida = new BitacoraPartida();
    }

    @Test
    @Transactional
    public void createBitacoraPartida() throws Exception {
        int databaseSizeBeforeCreate = bitacoraPartidaRepository.findAll().size();

        // Create the BitacoraPartida
        restBitacoraPartidaMockMvc.perform(post("/api/bitacoraPartidas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bitacoraPartida)))
                .andExpect(status().isCreated());

        // Validate the BitacoraPartida in the database
        List<BitacoraPartida> bitacoraPartidas = bitacoraPartidaRepository.findAll();
        assertThat(bitacoraPartidas).hasSize(databaseSizeBeforeCreate + 1);
        BitacoraPartida testBitacoraPartida = bitacoraPartidas.get(bitacoraPartidas.size() - 1);
    }

    @Test
    @Transactional
    public void getAllBitacoraPartidas() throws Exception {
        // Initialize the database
        bitacoraPartidaRepository.saveAndFlush(bitacoraPartida);

        // Get all the bitacoraPartidas
        restBitacoraPartidaMockMvc.perform(get("/api/bitacoraPartidas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bitacoraPartida.getId().intValue())));
    }

    @Test
    @Transactional
    public void getBitacoraPartida() throws Exception {
        // Initialize the database
        bitacoraPartidaRepository.saveAndFlush(bitacoraPartida);

        // Get the bitacoraPartida
        restBitacoraPartidaMockMvc.perform(get("/api/bitacoraPartidas/{id}", bitacoraPartida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bitacoraPartida.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBitacoraPartida() throws Exception {
        // Get the bitacoraPartida
        restBitacoraPartidaMockMvc.perform(get("/api/bitacoraPartidas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBitacoraPartida() throws Exception {
        // Initialize the database
        bitacoraPartidaRepository.saveAndFlush(bitacoraPartida);

		int databaseSizeBeforeUpdate = bitacoraPartidaRepository.findAll().size();

        // Update the bitacoraPartida
        restBitacoraPartidaMockMvc.perform(put("/api/bitacoraPartidas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bitacoraPartida)))
                .andExpect(status().isOk());

        // Validate the BitacoraPartida in the database
        List<BitacoraPartida> bitacoraPartidas = bitacoraPartidaRepository.findAll();
        assertThat(bitacoraPartidas).hasSize(databaseSizeBeforeUpdate);
        BitacoraPartida testBitacoraPartida = bitacoraPartidas.get(bitacoraPartidas.size() - 1);
    }

    @Test
    @Transactional
    public void deleteBitacoraPartida() throws Exception {
        // Initialize the database
        bitacoraPartidaRepository.saveAndFlush(bitacoraPartida);

		int databaseSizeBeforeDelete = bitacoraPartidaRepository.findAll().size();

        // Get the bitacoraPartida
        restBitacoraPartidaMockMvc.perform(delete("/api/bitacoraPartidas/{id}", bitacoraPartida.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BitacoraPartida> bitacoraPartidas = bitacoraPartidaRepository.findAll();
        assertThat(bitacoraPartidas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
