package ru.yastrebov.onemicr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yastrebov.onemicr.model.enums.Gender;
import ru.yastrebov.onemicr.model.enums.Position;
import ru.yastrebov.onemicr.model.enums.Project;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Double experience;
    private Position position;
    private Project project;
    private LocalDate hireDate;
    private Gender gender;

}
