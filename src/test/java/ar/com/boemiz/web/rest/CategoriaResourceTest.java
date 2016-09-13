package ar.com.boemiz.web.rest;

import ar.com.boemiz.Application;
import ar.com.boemiz.domain.Categoria;
import ar.com.boemiz.repository.CategoriaRepository;
import ar.com.boemiz.web.rest.mapper.CategoriaMapper;

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
 * Test class for the CategoriaResource REST controller.
 *
 * @see CategoriaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CategoriaResourceTest {

    private static final String DEFAULT_DESCRIPCION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPCION = "UPDATED_TEXT";

    @Inject
    private CategoriaRepository categoriaRepository;

    @Inject
    private CategoriaMapper categoriaMapper;

    private MockMvc restCategoriaMockMvc;

    private Categoria categoria;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoriaResource categoriaResource = new CategoriaResource();
        ReflectionTestUtils.setField(categoriaResource, "categoriaRepository", categoriaRepository);
        ReflectionTestUtils.setField(categoriaResource, "categoriaMapper", categoriaMapper);
        this.restCategoriaMockMvc = MockMvcBuilders.standaloneSetup(categoriaResource).build();
    }

    @Before
    public void initTest() {
        categoria = new Categoria();
        categoria.setDescripcion(DEFAULT_DESCRIPCION);
    }

    @Test
    @Ignore
    @Transactional
    public void createCategoria() throws Exception {
        int databaseSizeBeforeCreate = categoriaRepository.findAll().size();

        // Create the Categoria
        restCategoriaMockMvc.perform(post("/api/categorias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoria)))
                .andExpect(status().isCreated());

        // Validate the Categoria in the database
        List<Categoria> categorias = categoriaRepository.findAll();
        assertThat(categorias).hasSize(databaseSizeBeforeCreate + 1);
        Categoria testCategoria = categorias.get(categorias.size() - 1);
        assertThat(testCategoria.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Ignore
    @Transactional
    public void getAllCategorias() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get all the categorias
        restCategoriaMockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(categoria.getId().intValue())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Ignore
    @Transactional
    public void getCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", categoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(categoria.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoria() throws Exception {
        // Get the categoria
        restCategoriaMockMvc.perform(get("/api/categorias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Ignore
    @Transactional
    public void updateCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

		int databaseSizeBeforeUpdate = categoriaRepository.findAll().size();

        // Update the categoria
        categoria.setDescripcion(UPDATED_DESCRIPCION);
        restCategoriaMockMvc.perform(put("/api/categorias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoria)))
                .andExpect(status().isOk());

        // Validate the Categoria in the database
        List<Categoria> categorias = categoriaRepository.findAll();
        assertThat(categorias).hasSize(databaseSizeBeforeUpdate);
        Categoria testCategoria = categorias.get(categorias.size() - 1);
        assertThat(testCategoria.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Ignore
    @Transactional
    public void deleteCategoria() throws Exception {
        // Initialize the database
        categoriaRepository.saveAndFlush(categoria);

		int databaseSizeBeforeDelete = categoriaRepository.findAll().size();

        // Get the categoria
        restCategoriaMockMvc.perform(delete("/api/categorias/{id}", categoria.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Categoria> categorias = categoriaRepository.findAll();
        assertThat(categorias).hasSize(databaseSizeBeforeDelete - 1);
    }
}
