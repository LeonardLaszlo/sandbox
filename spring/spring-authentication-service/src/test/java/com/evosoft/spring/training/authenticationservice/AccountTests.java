package com.evosoft.spring.training.authenticationservice;

import com.evosoft.spring.training.authenticationservice.controller.AccountController;
import com.evosoft.spring.training.authenticationservice.domain.Account;
import com.evosoft.spring.training.authenticationservice.persistence.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountTests {
    private static final String BAD_PASSWORD = "bad_password";
    private static final String ENDPOINT_ROOT = "/accounts/";
    private static final String TEST_PASSWORD = "test_password";
    private static final String TEST_USER = "test_user";
    private static final String TEST_USERNAME = "test_username";

    private MockMvc mvc;
    private String endpoint;

    @Value("${authentication.username}")
    private String userName;

    @Value("${authentication.password}")
    private String password;

    @MockBean
    private AccountRepository repository;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        initMocks(this);
        this.mvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        this.endpoint = ENDPOINT_ROOT + this.userName;
    }

    @Test
    public void shouldGetAccount() throws Exception {
        given(this.repository.findByUsername(userName)).willReturn(getAccount());

        this.mvc.perform(get(endpoint).accept(APPLICATION_JSON).with(httpBasic(userName, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.fullName", is(TEST_USER)))
                .andExpect(jsonPath("$.password", is(TEST_PASSWORD)));
    }

    @Test
    public void shouldGetUnauthorized() throws Exception {
        this.mvc.perform(get(endpoint).accept(APPLICATION_JSON).with(httpBasic(userName, BAD_PASSWORD)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldPostAnAccount() throws Exception {
        final int maxNumberOfInvocations = 1;

        given(this.repository.findByUsername(userName)).willReturn(Optional.empty());

        this.mvc.perform(post(ENDPOINT_ROOT)
                .contentType(APPLICATION_JSON)
                .with(httpBasic(userName, password))
                .content(mapper.writeValueAsString(getAccount().orElseThrow(() -> new Exception("Are you nuts?")))))
                .andExpect(status().isCreated());

        verify(this.repository, atLeastOnce()).save(any(Account.class));
        verify(this.repository, atMost(maxNumberOfInvocations)).save(any(Account.class));
    }

    private Optional<Account> getAccount() {
        return of(Account.builder()
                .fullName(TEST_USER)
                .password(TEST_PASSWORD)
                .username(TEST_USERNAME)
                .build()
        );
    }
}
