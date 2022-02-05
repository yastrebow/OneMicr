package ru.yastrebov.onemicr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
@Tag(name = "Main Controller", description = "It is the main controller of OneMicr")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Получение списка всех работников из БД",
            description = "Позволяет получить список всех работников")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Получение записи об одном работнике",
            description = "Позволяет получить работника по id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Создание записи",
            description = "Позволяет создать в БД нового работника")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.create(employeeDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Изменение записи",
            description = "Позволяет отредактировать запись по id")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateById(@RequestBody EmployeeDto employeeDto, @PathVariable UUID id) {
        return new ResponseEntity<>(employeeService.updateById(employeeDto, id), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Удаление записи",
            description = "Позволяет удалить запись из БД по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
