package ru.yastrebov.onemicr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.repository.EmlpoyeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public EmlpoyeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee create(@RequestBody Employee emlpoyee) {
        return employeeRepository.save(emlpoyee);
    }

    @PutMapping("/{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable UUID id) {
        if (employeeRepository.existsById(id)) {
            return employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
