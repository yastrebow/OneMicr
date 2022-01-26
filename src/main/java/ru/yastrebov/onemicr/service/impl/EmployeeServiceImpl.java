package ru.yastrebov.onemicr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.kafka.KafkaProducer;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.mupstruct.EmployeeMapper;
import ru.yastrebov.onemicr.repository.EmployeeRepository;
import ru.yastrebov.onemicr.service.EmployeeService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(mapper::employeeToDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(UUID id) {
        Employee getEmployee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.employeeToDto(getEmployee);
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        final Employee savedEmployee = employeeRepository.save(mapper.dtoToEmployee(employeeDto));

        kafkaProducer.sendMessage("New record was created into " + kafkaProducer.createMessageForSending(employeeDto));
        return mapper.employeeToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, UUID id) {

        final Employee updatedEmployee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.updateFromDtoToEntity(employeeDto, updatedEmployee);
        final Employee savedEmployee = employeeRepository.save(updatedEmployee);

        kafkaProducer.sendMessage("This record was updated into " + kafkaProducer.createMessageForSending(employeeDto));

        return mapper.employeeToDto(savedEmployee);
    }

    @Override
    public void deleteEmployeeById(final UUID id) {

        final Employee employeeForDelete = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(employeeForDelete);

        final EmployeeDto deletedEmployeeDTO = mapper.employeeToDto(employeeForDelete);

        kafkaProducer.sendMessage("The record was deleted from " + kafkaProducer.createMessageForSending(deletedEmployeeDTO));
    }
}
