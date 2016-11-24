package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Pass;
import com.mycompany.myapp.repository.PassRepository;
import com.mycompany.myapp.service.PassService;

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
 * Test class for the PassResource REST controller.
 *
 * @see PassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class PassResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATED_AT);

    private static final Integer DEFAULT_FACILITY_ID = 1;
    private static final Integer UPDATED_FACILITY_ID = 2;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_END_DATE);

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    @Inject
    private PassRepository passRepository;

    @Inject
    private PassService passService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPassMockMvc;

    private Pass pass;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PassResource passResource = new PassResource();
        ReflectionTestUtils.setField(passResource, "passService", passService);
        this.restPassMockMvc = MockMvcBuilders.standaloneSetup(passResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pass createEntity() {
        Pass pass = new Pass()
                .createdAt(DEFAULT_CREATED_AT)
                .facilityId(DEFAULT_FACILITY_ID)
                .customerId(DEFAULT_CUSTOMER_ID)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .price(DEFAULT_PRICE);
        return pass;
    }

    @Before
    public void initTest() {
        passRepository.deleteAll();
        pass = createEntity();
    }

    @Test
    public void createPass() throws Exception {
        int databaseSizeBeforeCreate = passRepository.findAll().size();

        // Create the Pass

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isCreated());

        // Validate the Pass in the database
        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeCreate + 1);
        Pass testPass = passes.get(passes.size() - 1);
        assertThat(testPass.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPass.getFacilityId()).isEqualTo(DEFAULT_FACILITY_ID);
        assertThat(testPass.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPass.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPass.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPass.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setCreatedAt(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFacilityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setFacilityId(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setCustomerId(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setStartDate(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setEndDate(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = passRepository.findAll().size();
        // set the field null
        pass.setPrice(null);

        // Create the Pass, which fails.

        restPassMockMvc.perform(post("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pass)))
                .andExpect(status().isBadRequest());

        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPasses() throws Exception {
        // Initialize the database
        passRepository.save(pass);

        // Get all the passes
        restPassMockMvc.perform(get("/api/passes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pass.getId())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].facilityId").value(hasItem(DEFAULT_FACILITY_ID)))
                .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    public void getPass() throws Exception {
        // Initialize the database
        passRepository.save(pass);

        // Get the pass
        restPassMockMvc.perform(get("/api/passes/{id}", pass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pass.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.facilityId").value(DEFAULT_FACILITY_ID))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    public void getNonExistingPass() throws Exception {
        // Get the pass
        restPassMockMvc.perform(get("/api/passes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePass() throws Exception {
        // Initialize the database
        passService.save(pass);

        int databaseSizeBeforeUpdate = passRepository.findAll().size();

        // Update the pass
        Pass updatedPass = passRepository.findOne(pass.getId());
        updatedPass
                .createdAt(UPDATED_CREATED_AT)
                .facilityId(UPDATED_FACILITY_ID)
                .customerId(UPDATED_CUSTOMER_ID)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .price(UPDATED_PRICE);

        restPassMockMvc.perform(put("/api/passes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPass)))
                .andExpect(status().isOk());

        // Validate the Pass in the database
        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeUpdate);
        Pass testPass = passes.get(passes.size() - 1);
        assertThat(testPass.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPass.getFacilityId()).isEqualTo(UPDATED_FACILITY_ID);
        assertThat(testPass.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPass.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPass.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPass.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    public void deletePass() throws Exception {
        // Initialize the database
        passService.save(pass);

        int databaseSizeBeforeDelete = passRepository.findAll().size();

        // Get the pass
        restPassMockMvc.perform(delete("/api/passes/{id}", pass.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pass> passes = passRepository.findAll();
        assertThat(passes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
