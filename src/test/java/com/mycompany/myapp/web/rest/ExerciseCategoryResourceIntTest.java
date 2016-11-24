package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.ExerciseCategory;
import com.mycompany.myapp.repository.ExerciseCategoryRepository;
import com.mycompany.myapp.service.ExerciseCategoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExerciseCategoryResource REST controller.
 *
 * @see ExerciseCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class ExerciseCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ExerciseCategoryRepository exerciseCategoryRepository;

    @Inject
    private ExerciseCategoryService exerciseCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExerciseCategoryMockMvc;

    private ExerciseCategory exerciseCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseCategoryResource exerciseCategoryResource = new ExerciseCategoryResource();
        ReflectionTestUtils.setField(exerciseCategoryResource, "exerciseCategoryService", exerciseCategoryService);
        this.restExerciseCategoryMockMvc = MockMvcBuilders.standaloneSetup(exerciseCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExerciseCategory createEntity() {
        ExerciseCategory exerciseCategory = new ExerciseCategory()
                .name(DEFAULT_NAME);
        return exerciseCategory;
    }

    @Before
    public void initTest() {
        exerciseCategoryRepository.deleteAll();
        exerciseCategory = createEntity();
    }

    @Test
    public void createExerciseCategory() throws Exception {
        int databaseSizeBeforeCreate = exerciseCategoryRepository.findAll().size();

        // Create the ExerciseCategory

        restExerciseCategoryMockMvc.perform(post("/api/exercise-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseCategory)))
                .andExpect(status().isCreated());

        // Validate the ExerciseCategory in the database
        List<ExerciseCategory> exerciseCategories = exerciseCategoryRepository.findAll();
        assertThat(exerciseCategories).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseCategory testExerciseCategory = exerciseCategories.get(exerciseCategories.size() - 1);
        assertThat(testExerciseCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseCategoryRepository.findAll().size();
        // set the field null
        exerciseCategory.setName(null);

        // Create the ExerciseCategory, which fails.

        restExerciseCategoryMockMvc.perform(post("/api/exercise-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseCategory)))
                .andExpect(status().isBadRequest());

        List<ExerciseCategory> exerciseCategories = exerciseCategoryRepository.findAll();
        assertThat(exerciseCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllExerciseCategories() throws Exception {
        // Initialize the database
        exerciseCategoryRepository.save(exerciseCategory);

        // Get all the exerciseCategories
        restExerciseCategoryMockMvc.perform(get("/api/exercise-categories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseCategory.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getExerciseCategory() throws Exception {
        // Initialize the database
        exerciseCategoryRepository.save(exerciseCategory);

        // Get the exerciseCategory
        restExerciseCategoryMockMvc.perform(get("/api/exercise-categories/{id}", exerciseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseCategory.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingExerciseCategory() throws Exception {
        // Get the exerciseCategory
        restExerciseCategoryMockMvc.perform(get("/api/exercise-categories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateExerciseCategory() throws Exception {
        // Initialize the database
        exerciseCategoryService.save(exerciseCategory);

        int databaseSizeBeforeUpdate = exerciseCategoryRepository.findAll().size();

        // Update the exerciseCategory
        ExerciseCategory updatedExerciseCategory = exerciseCategoryRepository.findOne(exerciseCategory.getId());
        updatedExerciseCategory
                .name(UPDATED_NAME);

        restExerciseCategoryMockMvc.perform(put("/api/exercise-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExerciseCategory)))
                .andExpect(status().isOk());

        // Validate the ExerciseCategory in the database
        List<ExerciseCategory> exerciseCategories = exerciseCategoryRepository.findAll();
        assertThat(exerciseCategories).hasSize(databaseSizeBeforeUpdate);
        ExerciseCategory testExerciseCategory = exerciseCategories.get(exerciseCategories.size() - 1);
        assertThat(testExerciseCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void deleteExerciseCategory() throws Exception {
        // Initialize the database
        exerciseCategoryService.save(exerciseCategory);

        int databaseSizeBeforeDelete = exerciseCategoryRepository.findAll().size();

        // Get the exerciseCategory
        restExerciseCategoryMockMvc.perform(delete("/api/exercise-categories/{id}", exerciseCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseCategory> exerciseCategories = exerciseCategoryRepository.findAll();
        assertThat(exerciseCategories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
