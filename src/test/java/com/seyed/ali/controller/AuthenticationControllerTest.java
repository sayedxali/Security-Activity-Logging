package com.seyed.ali.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.service.interfaces.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static com.seyed.ali.model.response.StatusCode.BAD_REQUEST;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = AuthenticationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthenticationControllerTest {

    private @Autowired MockMvc mockMvc;
    private @Autowired ObjectMapper objectMapper;

    private @MockBean AuthenticationService authenticationService;

    private @Value("${api.endpoint.base-url}") String baseUrl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("registerUser should return success when inputs are valid")
    public void registerUser_ValidInputs_Success() throws Exception {
        // Given (Arrange) - Set up the conditions for your test.
        UserDTO userDTO = new UserDTO("1", "user", "USER", false);
        given(this.authenticationService.registerUser(Mockito.any(LogUser.class)))
                .willReturn(userDTO);

        LogUser logUser = new LogUser("1", "user", "user", false, "USER", Instant.now());
        String json = this.objectMapper.writeValueAsString(logUser);

        // When (Act) - Call the method you're testing.
        ResultActions response = this.mockMvc.perform(
                post(STR."\{this.baseUrl}/auth/register")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
        );

        // Then (Assert) - Check the result.
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.flag", is(true)))
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.message", is("User registration successful.")))
                .andExpect(jsonPath("$.data.userId", is("1")))
                .andExpect(jsonPath("$.data.username", is(userDTO.username())))
                .andExpect(jsonPath("$.data.roles", is(userDTO.roles())))
                .andExpect(jsonPath("$.data.enabled", is(userDTO.enabled())))
        ;
    }

    @Test
    @DisplayName("registerUser should fail when inputs are invalid")
    public void registerUser_InValidInputs_Exception() throws Exception {
        // Given (Arrange) - Set up the conditions for your test.
        UserDTO userDTO = new UserDTO("1", "", "", false);
        given(this.authenticationService.registerUser(Mockito.any(LogUser.class)))
                .willReturn(userDTO);

        LogUser logUser = new LogUser("1", "", "", false, "", Instant.now());
        String json = this.objectMapper.writeValueAsString(logUser);

        // When (Act) - Call the method you're testing.
        ResultActions response = this.mockMvc.perform(
                post(STR."\{this.baseUrl}/auth/register")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json)
        );

        // Then (Assert) - Check the result.
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.flag", is(false)))
                .andExpect(jsonPath("$.code", is(BAD_REQUEST)))
                .andExpect(jsonPath("$.message", is("Provided arguments are invalid, see data for details.")))
                .andExpect(jsonPath("$.data.password", is("password is required.")))
                .andExpect(jsonPath("$.data.username", is("username is required.")))
                .andExpect(jsonPath("$.data.roles", is("roles are required.")))
        ;
    }

}