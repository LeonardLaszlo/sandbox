package com.evosoft.spring.training.anagramservice.security;

import com.evosoft.spring.training.anagramservice.client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Optional.ofNullable;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationClient authenticationClient;

    @Autowired
    SecurityConfig(final AuthenticationClient client) {
        this.authenticationClient = client;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((String username) -> ofNullable(authenticationClient.findAccount(username).getBody())
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found.")));
    }
}
