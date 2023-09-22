package com.spaco_apoio.api.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableJpaRepositories(basePackages = {"com.spaco_apoio.api.repository"})
@EntityScan(basePackages = {"com.spaco_apoio.api.model"})
@ComponentScan(basePackages = {"com.spaco_apoio.api.*"})
@EnableAutoConfiguration
public class ApiConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
