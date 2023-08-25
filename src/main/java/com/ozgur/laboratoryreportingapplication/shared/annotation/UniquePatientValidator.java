package com.ozgur.laboratoryreportingapplication.shared.annotation;

import com.ozgur.laboratoryreportingapplication.entity.Patient;
import com.ozgur.laboratoryreportingapplication.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Optional;

public class UniquePatientValidator implements ConstraintValidator<UniquePatient, Map<String, String>> {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public boolean isValid(Map<String, String> patientInfo, ConstraintValidatorContext context) {

        try {
            Optional<Patient> patient = patientRepository.findByIdNumber(patientInfo.get("idNumber"));
            return patient.map(value -> value.getIdNumber().equals(patientInfo.get("idNumber").trim()) &&
                    value.getName().equals(patientInfo.get("name").trim()) &&
                    value.getSurname().equals(patientInfo.get("surname").trim())).orElse(true);

        } catch (Exception ignored) {
        }
        return false;
    }
}
