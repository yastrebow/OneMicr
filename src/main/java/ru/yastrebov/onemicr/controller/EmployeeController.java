package ru.yastrebov.onemicr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.service.EmployeeService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable UUID id) {

        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee emlpoyee) {

        return employeeService.create(emlpoyee);
    }

    @PatchMapping("/{id}")
    public Employee updateByID(@RequestBody Employee employee, @PathVariable UUID id) {
        return employeeService.updateById(employee, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {

        employeeService.delete(id);
    }
}
