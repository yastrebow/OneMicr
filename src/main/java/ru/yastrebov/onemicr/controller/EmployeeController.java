package ru.yastrebov.onemicr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.service.impl.EmployeeServiceImpl;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable UUID id) {
        return employeeServiceImpl.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        return employeeServiceImpl.create(employeeDto);
    }

    @PatchMapping("/{id}")
    public EmployeeDto updateByID(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        return employeeServiceImpl.updateById(employeeDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employeeServiceImpl.deleteEmployeeById(id);
    }
}
