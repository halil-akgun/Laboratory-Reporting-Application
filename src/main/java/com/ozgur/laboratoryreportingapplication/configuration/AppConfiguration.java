package com.ozgur.laboratoryreportingapplication.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "laboratory")
// ConfigurationProperties: Variables in yml will be assigned to variables in this class
public class AppConfiguration {

    private String uploadPathForProfilePicture;
    private String uploadPathForReportPicture;
//    in the yml: upload-path-for-report-picture, in the class: uploadPathForReportPicture (correct usage in this way)
}
