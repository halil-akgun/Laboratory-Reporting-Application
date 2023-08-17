package com.ozgur.laboratoryreportingapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/profile/**")
                .addResourceLocations("file:./" + appConfiguration.getUploadPathForProfilePicture())
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/images/report/**")
                .addResourceLocations("file:./" + appConfiguration.getUploadPathForReportPicture())
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createStorageDirectories() {
        return (args) -> {
            File folder = new File(appConfiguration.getUploadPathForProfilePicture());
            File folder2 = new File(appConfiguration.getUploadPathForReportPicture());
            if (!folder.exists()) folder.mkdirs();
            if (!folder2.exists()) folder2.mkdirs();
        };
    }
}
