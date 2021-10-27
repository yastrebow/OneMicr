package ru.yastrebov.onemicr.model;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
public class Emlpoyee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "experience")
    private Double experience;

    @Column(name = "position", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "project", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private Project project;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "gender", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private Gender gender;

 }
    enum Position {EMPLOYEE, MANAGER, DIRECTOR}

    enum Project {PROJECT1, PROJECT2, PROJECT3}

    enum Gender {MALE, FEMALE, INDEFINED}



