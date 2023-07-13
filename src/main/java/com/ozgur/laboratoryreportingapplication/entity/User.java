package com.ozgur.laboratoryreportingapplication.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
	
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
