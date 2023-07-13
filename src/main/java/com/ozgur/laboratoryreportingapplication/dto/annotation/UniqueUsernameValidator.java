package com.ozgur.laboratoryreportingapplication.dto.annotation;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null) return true;

        Optional<User> user = userRepository.findByUsername(username);

        return user.isEmpty();
    }
}
