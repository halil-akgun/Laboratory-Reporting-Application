package com.ozgur.laboratoryreportingapplication.entity.abstracts;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ozgur.laboratoryreportingapplication.entity.concretes.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass// The fields in this class are created in the tables in the db of the entity classes that extend this class.
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(length = 25, nullable = false, unique = true)
    private String username;
	
    @Column(length = 25, nullable = false)
    private String name;
    
    @Column(length = 25, nullable = false)
	private String surname;
    
    @Column(length = 7, nullable = false, unique = true)
	private String hospitalIdNumber;
    
    @Column(length = 255, nullable = false)
    private String password;

    @ManyToOne
    private UserRole userRole;

}
