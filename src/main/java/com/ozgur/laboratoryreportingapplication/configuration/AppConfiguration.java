package com.ozgur.laboratoryreportingapplication.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "laboratory")
// ConfigurationProperties: Variables in yml will be assigned to variables in this class
public class AppConfiguration {

    private String uploadPath;
//    in yml: upload-path, in the class: uploadPath (correct usage in this way)
}
