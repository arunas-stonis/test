package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Partner;
import com.mycompany.myapp.repository.PartnerRepository;
import com.mycompany.myapp.service.PartnerService;

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
 * Test class for the PartnerResource REST controller.
 *
 * @see PartnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class PartnerResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATED_AT);

    private static final Integer DEFAULT_ACCOUNT_ID = 1;
    private static final Integer UPDATED_ACCOUNT_ID = 2;

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private PartnerService partnerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartnerResource partnerResource = new PartnerResource();
        ReflectionTestUtils.setField(partnerResource, "partnerService", partnerService);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partner createEntity() {
        Partner partner = new Partner()
                .createdAt(DEFAULT_CREATED_AT)
                .accountId(DEFAULT_ACCOUNT_ID)
                .name(DEFAULT_NAME);
        return partner;
    }

    @Before
    public void initTest() {
        partnerRepository.deleteAll();
        partner = createEntity();
    }

    @Test
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPartner.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testPartner.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCreatedAt(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAccountIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setAccountId(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setName(null);

        // Create the Partner, which fails.

        restPartnerMockMvc.perform(post("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partner)))
                .andExpect(status().isBadRequest());

        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.save(partner);

        // Get all the partners
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.save(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findOne(partner.getId());
        updatedPartner
                .createdAt(UPDATED_CREATED_AT)
                .accountId(UPDATED_ACCOUNT_ID)
                .name(UPDATED_NAME);

        restPartnerMockMvc.perform(put("/api/partners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPartner)))
                .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partners.get(partners.size() - 1);
        assertThat(testPartner.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPartner.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testPartner.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerService.save(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Get the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partner> partners = partnerRepository.findAll();
        assertThat(partners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
