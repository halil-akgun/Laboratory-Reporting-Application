package com.ozgur.laboratoryreportingapplication.service;

import com.ozgur.laboratoryreportingapplication.entity.Report;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.ReportRepository;
import com.ozgur.laboratoryreportingapplication.shared.ReportResponse;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveUpdateRequest;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.utils.FileService;
import com.ozgur.laboratoryreportingapplication.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final Mapper mapper;
    private final FileService fileService;

    public ResponseMessage<ReportResponse> saveReport(ReportSaveUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserPojoWithUsername(username);
        Report report = mapper.createReportFromReportSaveUpdateRequest(request);
        report.setUser(user);
        if (request.getImageOfReport() != null) {
            try {
                String imageName = fileService.writeBase64EncodedStringToFileForReportPicture(request.getImageOfReport());
                report.setImageOfReport(imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Report savedReport = reportRepository.save(report);

        return ResponseMessage.<ReportResponse>builder()
                .message("Report saved successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(mapper.createReportResponseFromReport(savedReport)).build();
    }

    public Page<ReportResponse> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable).map(mapper::createReportResponseFromReport);
    }
}
