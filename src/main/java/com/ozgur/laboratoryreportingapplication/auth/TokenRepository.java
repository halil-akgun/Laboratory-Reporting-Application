package com.ozgur.laboratoryreportingapplication.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByLastActionTimeBefore(LocalDateTime threeHoursAgo);
}
