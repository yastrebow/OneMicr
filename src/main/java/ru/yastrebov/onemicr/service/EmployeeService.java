package ru.yastrebov.onemicr.service;

import ru.yastrebov.onemicr.dto.EmployeeDto;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeDto> getAll();

    EmployeeDto getEmployeeById(UUID id);

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto updateById(final EmployeeDto employeeDTO, final UUID id);

    void deleteEmployeeById(final UUID id);
}
