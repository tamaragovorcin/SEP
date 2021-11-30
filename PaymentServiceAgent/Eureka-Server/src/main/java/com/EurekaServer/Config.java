package com.EurekaServer;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Configuration
public class Config  implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "DELETE", "UPDATE", "FETCH", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(-1);
    }
}
