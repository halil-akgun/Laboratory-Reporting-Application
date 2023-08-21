package com.ozgur.laboratoryreportingapplication.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // method-level access control by role
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    @Bean // in the old version we did this by @Override the configure method
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// cors(): browser-based security - when a request is received from a different
// location (domain,protocol,port), the browser automatically blocks if they do not match
// the domains of the server. but nowadays generally the backend and frontend are on different servers.
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(AUTH_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
                .and()
                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    private static final String[] AUTH_WHITE_LIST = {
            "/",
            "/index*",
            "/images/**",
            "/laboratory-icon.ico",
            "/static/**",
            "/*.js",
            "/*.json",
            "/users/save",
            "/auth"
    };
}