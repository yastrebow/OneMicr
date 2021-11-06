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

    UUID id;
    String firstName;
    String lastName;
    Integer age;
    Double experience;
    Position position;
    Project project;
    LocalDate hireDate;
    Gender gender;

}
