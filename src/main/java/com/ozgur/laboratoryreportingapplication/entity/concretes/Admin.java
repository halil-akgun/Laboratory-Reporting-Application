package com.ozgur.laboratoryreportingapplication.entity.concretes;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ozgur.laboratoryreportingapplication.entity.abstracts.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends User{

}
