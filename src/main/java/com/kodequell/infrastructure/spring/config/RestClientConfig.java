package com.kodequell.infrastructure.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient addressRepository(@Value("${customer.api.url}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
