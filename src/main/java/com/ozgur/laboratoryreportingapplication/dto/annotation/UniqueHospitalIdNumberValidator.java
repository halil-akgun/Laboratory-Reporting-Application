package com.ozgur.laboratoryreportingapplication.dto.annotation;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueHospitalIdNumberValidator implements ConstraintValidator<UniqueHospitalIdNumber, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String hospitalIdNumber, ConstraintValidatorContext context) {

        if (hospitalIdNumber == null) return true;

        Optional<User> user = userRepository.findByHospitalIdNumber(hospitalIdNumber);

        return user.isEmpty();
    }

}
