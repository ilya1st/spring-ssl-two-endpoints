package com.example.ssltest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Value("#{${server.externalRoutes}}")
    private Set<String> externalRoutesSet;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()).x509(withDefaults());

        return http.build();
    }

    // @formatter:off
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("OU=111, O=GOPA, L=Tabmov, ST=Tambov, C=RU")
                .password("password")
                .roles("USER")
                .build();
        UserDetails dianne = User.withDefaultPasswordEncoder()
                .username("dianne")
                .password("password")
                .roles("USER")
                .build();
        UserDetails rod = User.withDefaultPasswordEncoder()
                .username("rod")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        UserDetails scott = User.withDefaultPasswordEncoder()
                .username("scott")
                .password("password")
                .roles("USER")
                .build();
        UserDetails bob = User.withDefaultPasswordEncoder()
                .username("UncleBob")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, dianne, rod, scott, bob);
    }
    // @formatter:on

}