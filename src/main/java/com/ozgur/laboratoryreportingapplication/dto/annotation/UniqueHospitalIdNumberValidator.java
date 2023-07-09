package com.ozgur.laboratoryreportingapplication.dto.annotation;

import com.ozgur.laboratoryreportingapplication.entity.concretes.Assistant;
import com.ozgur.laboratoryreportingapplication.repository.AssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UniqueHospitalIdNumberValidator implements ConstraintValidator<UniqueHospitalIdNumber, String> {

    @Autowired
    private AssistantRepository assistantRepository;

    @Override
    public boolean isValid(String hospitalIdNumber, ConstraintValidatorContext context) {

        if (hospitalIdNumber == null) return true;

        Optional<Assistant> assistant = assistantRepository.findByHospitalIdNumber(hospitalIdNumber);

        return assistant.isEmpty();
    }

}
