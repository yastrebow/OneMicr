package ru.yastrebov.onemicr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.yastrebov.onemicr.model.enums.Gender;
import ru.yastrebov.onemicr.model.enums.Position;
import ru.yastrebov.onemicr.model.enums.Project;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
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




