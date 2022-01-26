package ru.yastrebov.onemicr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Schema(description = "Работник")
public class EmployeeDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Имя работника", example = "Larisa")
    private String firstName;

    @Schema(description = "Фамилия работника", example = "Yastrebova")
    private String lastName;

    @Schema(description = "Возраст работника")
    private Integer age;

    @Schema(description = "Опыт работника")
    private Double experience;

    @Schema(description = "Позиция работника")
    private Position position;

    @Schema(description = "Проект, в котором работник задействован")
    private Project project;

    @Schema(description = "Дата принятия работника на работу")
    private LocalDate hireDate;

    @Schema(description = "Пол работника")
    private Gender gender;

}
