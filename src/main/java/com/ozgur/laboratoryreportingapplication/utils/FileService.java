package com.ozgur.laboratoryreportingapplication.utils;

import com.ozgur.laboratoryreportingapplication.configuration.AppConfiguration;
import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    AppConfiguration appConfiguration;
    Tika tika;

    public FileService(AppConfiguration appConfiguration) {
        super();
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
    }

    public String writeBase64EncodedStringToFileForProfilePicture(String image) throws IOException {
        String imageName = generateRandomName();
        File target = new File(appConfiguration.getUploadPathForProfilePicture() + imageName);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64encoded);
        outputStream.close();
        return imageName;
    }

    public String writeBase64EncodedStringToFileForReportPicture(String image) throws IOException {
        String imageName = generateRandomName();
        File target = new File(appConfiguration.getUploadPathForReportPicture() + imageName);
        OutputStream outputStream = new FileOutputStream(target);
        byte[] base64encoded = Base64.getDecoder().decode(image);
        outputStream.write(base64encoded);
        outputStream.close();
        return imageName;
    }

    private String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void deleteProfileImage(String imageName) {
        if (imageName == null) return;
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPathForProfilePicture(), imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReportImage(String imageName) {
        if (imageName == null) return;
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPathForReportPicture(), imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }

    public void deleteReportImagesOfUser(User user) {
        for (Report report : user.getReports()) {
            if (!report.getImageOfReport().equals("sampleReport.png")) {
                deleteReportImage(report.getImageOfReport());
            }
        }
    }
}
