package com.example.ssltest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ExternalWebMvcConfiguration implements WebMvcConfigurer {

    private final int externalPort;
    private final Set<String> externalRoutesSet;
    public ExternalWebMvcConfiguration(
            @Value("${server.externalPort}") int externalPort,
            @Value("#{${server.externalRoutes}}") List<String> externalRoutes)
    {
            this.externalPort = externalPort;
            externalRoutesSet = new HashSet<>(externalRoutes);
    }
    @Bean
    public FilterRegistrationBean<ExternalEndpointsFilter> trustedEndpointsFilter() {
        return new FilterRegistrationBean<>(new ExternalEndpointsFilter(externalPort, externalRoutesSet));
    }
}
