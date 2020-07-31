package com.evosoft.spring.training.anagramservice;

import com.evosoft.spring.training.anagramservice.client.AuthenticationClient;
import com.evosoft.spring.training.anagramservice.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnagramTests {
    private MockMvc mvc;

    @Value("${authentication.username}")
    private String userName;

    @Value("${authentication.password}")
    private String password;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthenticationClient authenticationClient;

    @Before
    public void setup() {
        mvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldGetAnagram() throws Exception {
        given(this.authenticationClient.findAccount(userName)).willReturn(getAccount());

        this.mvc.perform(get("/anagram/secure").accept(MediaType.APPLICATION_JSON).with(httpBasic(userName, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.original", is("secure")))
                .andExpect(jsonPath("$.anagramList[0]", is("cereus")))
                .andExpect(jsonPath("$.anagramList[1]", is("recuse")))
                .andExpect(jsonPath("$.anagramList[2]", is("rescue")))
                .andExpect(jsonPath("$.anagramList[3]", is("secure")));
    }

    @Test
    public void shouldCountWordsWithLength3() throws Exception {
        final int frequency = 601;
        final int length = 3;

        given(this.authenticationClient.findAccount(userName)).willReturn(getAccount());

        this.mvc.perform(get("/length/3").accept(MediaType.APPLICATION_JSON).with(httpBasic(userName, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frequency", is(frequency)))
                .andExpect(jsonPath("$.lengthOfWord", is(length)));
    }

    @Test
    public void shouldGetStatistics() throws Exception {
        final int longest = 22;
        final int shortest = 1;
        final double average = 7.355424376209137;

        given(this.authenticationClient.findAccount(userName)).willReturn(getAccount());

        this.mvc.perform(get("/statistics").accept(MediaType.APPLICATION_JSON).with(httpBasic(userName, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortestWord", is(shortest)))
                .andExpect(jsonPath("$.longestWord", is(longest)))
                .andExpect(jsonPath("$.averageLengthOfWords", is(average)));
    }

    private ResponseEntity<Account> getAccount() {
        return new ResponseEntity<>(Account.builder()
                .fullName(userName)
                .password(password)
                .username(userName)
                .build(), HttpStatus.OK);
    }
}
