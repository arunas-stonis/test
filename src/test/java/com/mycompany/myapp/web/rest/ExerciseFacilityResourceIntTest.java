package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.ExerciseFacility;
import com.mycompany.myapp.repository.ExerciseFacilityRepository;
import com.mycompany.myapp.service.ExerciseFacilityService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExerciseFacilityResource REST controller.
 *
 * @see ExerciseFacilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class ExerciseFacilityResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATED_AT);

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ExerciseFacilityRepository exerciseFacilityRepository;

    @Inject
    private ExerciseFacilityService exerciseFacilityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExerciseFacilityMockMvc;

    private ExerciseFacility exerciseFacility;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseFacilityResource exerciseFacilityResource = new ExerciseFacilityResource();
        ReflectionTestUtils.setField(exerciseFacilityResource, "exerciseFacilityService", exerciseFacilityService);
        this.restExerciseFacilityMockMvc = MockMvcBuilders.standaloneSetup(exerciseFacilityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExerciseFacility createEntity() {
        ExerciseFacility exerciseFacility = new ExerciseFacility()
                .createdAt(DEFAULT_CREATED_AT)
                .name(DEFAULT_NAME)
                .categoryId(DEFAULT_CATEGORY_ID)
                .address(DEFAULT_ADDRESS)
                .description(DEFAULT_DESCRIPTION);
        return exerciseFacility;
    }

    @Before
    public void initTest() {
        exerciseFacilityRepository.deleteAll();
        exerciseFacility = createEntity();
    }

    @Test
    public void createExerciseFacility() throws Exception {
        int databaseSizeBeforeCreate = exerciseFacilityRepository.findAll().size();

        // Create the ExerciseFacility

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isCreated());

        // Validate the ExerciseFacility in the database
        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseFacility testExerciseFacility = exerciseFacilities.get(exerciseFacilities.size() - 1);
        assertThat(testExerciseFacility.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testExerciseFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExerciseFacility.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testExerciseFacility.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testExerciseFacility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFacilityRepository.findAll().size();
        // set the field null
        exerciseFacility.setCreatedAt(null);

        // Create the ExerciseFacility, which fails.

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isBadRequest());

        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFacilityRepository.findAll().size();
        // set the field null
        exerciseFacility.setName(null);

        // Create the ExerciseFacility, which fails.

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isBadRequest());

        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFacilityRepository.findAll().size();
        // set the field null
        exerciseFacility.setCategoryId(null);

        // Create the ExerciseFacility, which fails.

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isBadRequest());

        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFacilityRepository.findAll().size();
        // set the field null
        exerciseFacility.setAddress(null);

        // Create the ExerciseFacility, which fails.

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isBadRequest());

        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseFacilityRepository.findAll().size();
        // set the field null
        exerciseFacility.setDescription(null);

        // Create the ExerciseFacility, which fails.

        restExerciseFacilityMockMvc.perform(post("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseFacility)))
                .andExpect(status().isBadRequest());

        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllExerciseFacilities() throws Exception {
        // Initialize the database
        exerciseFacilityRepository.save(exerciseFacility);

        // Get all the exerciseFacilities
        restExerciseFacilityMockMvc.perform(get("/api/exercise-facilities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseFacility.getId())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID)))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getExerciseFacility() throws Exception {
        // Initialize the database
        exerciseFacilityRepository.save(exerciseFacility);

        // Get the exerciseFacility
        restExerciseFacilityMockMvc.perform(get("/api/exercise-facilities/{id}", exerciseFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseFacility.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingExerciseFacility() throws Exception {
        // Get the exerciseFacility
        restExerciseFacilityMockMvc.perform(get("/api/exercise-facilities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateExerciseFacility() throws Exception {
        // Initialize the database
        exerciseFacilityService.save(exerciseFacility);

        int databaseSizeBeforeUpdate = exerciseFacilityRepository.findAll().size();

        // Update the exerciseFacility
        ExerciseFacility updatedExerciseFacility = exerciseFacilityRepository.findOne(exerciseFacility.getId());
        updatedExerciseFacility
                .createdAt(UPDATED_CREATED_AT)
                .name(UPDATED_NAME)
                .categoryId(UPDATED_CATEGORY_ID)
                .address(UPDATED_ADDRESS)
                .description(UPDATED_DESCRIPTION);

        restExerciseFacilityMockMvc.perform(put("/api/exercise-facilities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedExerciseFacility)))
                .andExpect(status().isOk());

        // Validate the ExerciseFacility in the database
        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeUpdate);
        ExerciseFacility testExerciseFacility = exerciseFacilities.get(exerciseFacilities.size() - 1);
        assertThat(testExerciseFacility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testExerciseFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExerciseFacility.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testExerciseFacility.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testExerciseFacility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void deleteExerciseFacility() throws Exception {
        // Initialize the database
        exerciseFacilityService.save(exerciseFacility);

        int databaseSizeBeforeDelete = exerciseFacilityRepository.findAll().size();

        // Get the exerciseFacility
        restExerciseFacilityMockMvc.perform(delete("/api/exercise-facilities/{id}", exerciseFacility.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseFacility> exerciseFacilities = exerciseFacilityRepository.findAll();
        assertThat(exerciseFacilities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
