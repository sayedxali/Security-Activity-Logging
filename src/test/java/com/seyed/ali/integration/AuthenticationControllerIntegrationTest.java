package com.seyed.ali.integration;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@DisplayName("Integration test for Demo api endpoints")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    private @Autowired MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    private String baseUrl;
    private String token;

    @BeforeEach
    void setUp() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
                        post(STR."\{this.baseUrl}/auth/login")
                                .with(httpBasic("admin", "admin"))
                )
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);

        this.token = STR."Bearer \{json.getJSONObject("data").getString("token")}";
    }

    @Test
    @DisplayName("userEndpoint should return success when admin tries the endpoint (GET)")
    public void userEndpoint_AdminUser_Success() throws Exception {
        ResultActions response = this.mockMvc.perform(
                get(STR."\{this.baseUrl}/authorize/user-admin")
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, this.token)
        );
        
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("This endpoint is authorized and can be accessed by ROLE_user & ROLE_admin")))
        ;
    }

    @Test
    @DisplayName("adminEndpoint should return forbidden when user tries the endpoint (GET)")
    public void userEndpoint_userUser_Forbidden() throws Exception {
        MockHttpServletResponse loginResponse = this.mockMvc.perform(
                post(STR."\{this.baseUrl}/auth/login")
                        .with(httpBasic("user", "user"))
        ).andReturn().getResponse();
        String contentAsString = loginResponse.getContentAsString();
        JSONObject json = new JSONObject(contentAsString);

        String jwt = json.getJSONObject("data").getString("token");
        this.token = STR."Bearer \{jwt}";

        ResultActions response = this.mockMvc.perform(
                get(STR."\{this.baseUrl}/authorize/admin")
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, this.token)
        );

        response.andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.flag", is(false)))
                .andExpect(jsonPath("$.code", is(403)))
                .andExpect(jsonPath("$.message", is("No permission.")))
                .andExpect(jsonPath("$.data", is("Access Denied")))
        ;
    }

}
