package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.Invitation;
import com.mycompany.myapp.repository.InvitationRepository;
import com.mycompany.myapp.service.InvitationService;

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
 * Test class for the InvitationResource REST controller.
 *
 * @see InvitationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class InvitationResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATED_AT);

    private static final ZonedDateTime DEFAULT_VALID_UNTIL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_VALID_UNTIL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_VALID_UNTIL_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_VALID_UNTIL);

    private static final Integer DEFAULT_CATAGERY_ID = 1;
    private static final Integer UPDATED_CATAGERY_ID = 2;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    @Inject
    private InvitationRepository invitationRepository;

    @Inject
    private InvitationService invitationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInvitationMockMvc;

    private Invitation invitation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvitationResource invitationResource = new InvitationResource();
        ReflectionTestUtils.setField(invitationResource, "invitationService", invitationService);
        this.restInvitationMockMvc = MockMvcBuilders.standaloneSetup(invitationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitation createEntity() {
        Invitation invitation = new Invitation()
                .createdAt(DEFAULT_CREATED_AT)
                .validUntil(DEFAULT_VALID_UNTIL)
                .catageryId(DEFAULT_CATAGERY_ID)
                .customerId(DEFAULT_CUSTOMER_ID);
        return invitation;
    }

    @Before
    public void initTest() {
        invitationRepository.deleteAll();
        invitation = createEntity();
    }

    @Test
    public void createInvitation() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation

        restInvitationMockMvc.perform(post("/api/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isCreated());

        // Validate the Invitation in the database
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeCreate + 1);
        Invitation testInvitation = invitations.get(invitations.size() - 1);
        assertThat(testInvitation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testInvitation.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testInvitation.getCatageryId()).isEqualTo(DEFAULT_CATAGERY_ID);
        assertThat(testInvitation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setCreatedAt(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isBadRequest());

        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setValidUntil(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isBadRequest());

        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCatageryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setCatageryId(null);

        // Create the Invitation, which fails.

        restInvitationMockMvc.perform(post("/api/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isBadRequest());

        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllInvitations() throws Exception {
        // Initialize the database
        invitationRepository.save(invitation);

        // Get all the invitations
        restInvitationMockMvc.perform(get("/api/invitations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL_STR)))
                .andExpect(jsonPath("$.[*].catageryId").value(hasItem(DEFAULT_CATAGERY_ID)))
                .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)));
    }

    @Test
    public void getInvitation() throws Exception {
        // Initialize the database
        invitationRepository.save(invitation);

        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitation.getId()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL_STR))
            .andExpect(jsonPath("$.catageryId").value(DEFAULT_CATAGERY_ID))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID));
    }

    @Test
    public void getNonExistingInvitation() throws Exception {
        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInvitation() throws Exception {
        // Initialize the database
        invitationService.save(invitation);

        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Update the invitation
        Invitation updatedInvitation = invitationRepository.findOne(invitation.getId());
        updatedInvitation
                .createdAt(UPDATED_CREATED_AT)
                .validUntil(UPDATED_VALID_UNTIL)
                .catageryId(UPDATED_CATAGERY_ID)
                .customerId(UPDATED_CUSTOMER_ID);

        restInvitationMockMvc.perform(put("/api/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInvitation)))
                .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeUpdate);
        Invitation testInvitation = invitations.get(invitations.size() - 1);
        assertThat(testInvitation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testInvitation.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testInvitation.getCatageryId()).isEqualTo(UPDATED_CATAGERY_ID);
        assertThat(testInvitation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    public void deleteInvitation() throws Exception {
        // Initialize the database
        invitationService.save(invitation);

        int databaseSizeBeforeDelete = invitationRepository.findAll().size();

        // Get the invitation
        restInvitationMockMvc.perform(delete("/api/invitations/{id}", invitation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
