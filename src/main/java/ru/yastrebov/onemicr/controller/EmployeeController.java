package ru.yastrebov.onemicr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody EmployeeDto employeeDto) {
    return new ResponseEntity<>(employeeService.create(employeeDto), HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateById(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.updateById(employeeDto, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
