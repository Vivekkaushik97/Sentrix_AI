package com.sentrix.ai.cve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CveConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
