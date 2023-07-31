package com.ozgur.laboratoryreportingapplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/users/images/**")
                .addResourceLocations("file:./uploads/profilepictures/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
}
