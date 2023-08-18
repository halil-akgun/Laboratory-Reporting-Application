package com.ozgur.laboratoryreportingapplication.auth;

import com.ozgur.laboratoryreportingapplication.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
