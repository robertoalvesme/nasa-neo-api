package br.com.rhfactor.nasaneoapi.controllers;

import br.com.rhfactor.nasaneoapi.H2DatabaseTest;
import br.com.rhfactor.nasaneoapi.dtos.JwtResponse;
import br.com.rhfactor.nasaneoapi.dtos.SignupForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
@H2DatabaseTest
@AutoConfigureMockMvc
@WithAnonymousUser
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateCredentialAndLogin() throws Exception {

        String signUpURI = AuthenticationController.PATH
                .concat("/signup");

        String signInURI = AuthenticationController.PATH
                .concat("/signin");

        ObjectMapper objectMapper = new ObjectMapper();

        SignupForm signupForm = SignupForm.builder()
                .username("someuser@email.com")
                .password("senha1234")
                .passwordConfirmation("senha1234")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(signUpURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(signupForm) )
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());


        SignupForm signIn = SignupForm.builder()
                .username("someuser@email.com")
                .password("senha1234")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(signInURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signIn))
                )
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JwtResponse token = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);
        assertThat(token, hasProperty("username", equalTo(signupForm.getUsername())));

    }


}
