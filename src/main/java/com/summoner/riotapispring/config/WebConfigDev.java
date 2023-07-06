package com.summoner.riotapispring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("dev")
@Configuration
public class WebConfigDev implements WebMvcConfigurer {

    @Value("${resource.location.dev}")
    private String resourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/icons/**")
                .addResourceLocations(resourceLocation + "images/icons/");

        registry.addResourceHandler("/images/champions/**")
                .addResourceLocations(resourceLocation + "images/champions/");
    }

}
