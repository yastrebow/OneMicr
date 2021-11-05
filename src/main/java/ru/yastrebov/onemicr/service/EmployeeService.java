package ru.yastrebov.onemicr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.repository.EmlpoyeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmlpoyeeRepository employeeRepository;

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(UUID id) {
        if(employeeRepository.existsById(id)) {
            return employeeRepository.getById(id);
        }
        else {
            throw new EntityNotFoundException();
        }
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateById(Employee employee, UUID id) {
        if (employeeRepository.existsById(id)) {
            return employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

}
