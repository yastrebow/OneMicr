package ru.yastrebov.onemicr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable UUID id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
        return employeeService.create(employeeDto);
    }

    @PatchMapping("/{id}")
    public EmployeeDto updateByID(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        return employeeService.updateById(employeeDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);
    }
}
