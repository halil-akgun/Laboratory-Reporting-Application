package com.ozgur.laboratoryreportingapplication.repository;

import com.ozgur.laboratoryreportingapplication.entity.Token;
import com.ozgur.laboratoryreportingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByLastActionTimeBefore(LocalDateTime threeHoursAgo);

    List<Token> findByUser(User user);
}
