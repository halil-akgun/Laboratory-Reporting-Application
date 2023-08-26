package com.ozgur.laboratoryreportingapplication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
public class Token {

    @Id
    private String token;

    @ManyToOne // user can login on multiple devices
    private User user;

    private LocalDateTime lastActionTime;
}
