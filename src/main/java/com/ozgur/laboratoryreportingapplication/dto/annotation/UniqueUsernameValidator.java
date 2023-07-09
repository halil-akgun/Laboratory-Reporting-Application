package com.ozgur.laboratoryreportingapplication.dto.annotation;

import com.ozgur.laboratoryreportingapplication.entity.concretes.Assistant;
import com.ozgur.laboratoryreportingapplication.repository.AssistantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AssistantRepository assistantRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        Optional<Assistant> assistant = assistantRepository.findByUsername(username);

        return assistant.isEmpty();
    }
}
