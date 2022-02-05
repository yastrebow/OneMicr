package ru.yastrebov.onemicr.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<EmployeeDto> getAll() {
        log.debug("getAll() - start");
        List<Employee> employeeList = employeeRepository.findAll();
        log.debug("getAll() - end");

        return employeeList.stream()
                .map(mapper::employeeToDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(UUID id) {
        log.debug("getEmployeeById() - start, id = {}", id);
        Employee getEmployee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        EmployeeDto employeeDto = mapper.employeeToDto(getEmployee);
        log.debug("getEmployeeById() - end, employeeDto = {}", employeeDto);

        return employeeDto;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        log.debug("create() - start, employeeDto = {}", employeeDto);
        final Employee savedEmployee = employeeRepository.save(mapper.dtoToEmployee(employeeDto));
        kafkaProducer.sendMessage("New record was created into " + kafkaProducer.createMessageForSending(employeeDto));
        log.debug("create() - end, employeeDto = {} created", employeeDto);

        return mapper.employeeToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, UUID id) {
        log.debug("updateById() - start, employeeDto = {}, id = {}", employeeDto, id);
        final Employee updatedEmployee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        mapper.updateFromDtoToEntity(employeeDto, updatedEmployee);
        final Employee savedEmployee = employeeRepository.save(updatedEmployee);
        log.debug("updateById() - end, employeeDto = {}, id = {} was update", employeeDto, id);
        kafkaProducer.sendMessage("This record was updated into " + kafkaProducer.createMessageForSending(employeeDto));

        return mapper.employeeToDto(savedEmployee);
    }

    @Override
    public void deleteEmployeeById(final UUID id) {
        log.debug("deleteEmployeeById() - start, id = {}", id);
        final Employee employeeForDelete = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        employeeRepository.delete(employeeForDelete);
        final EmployeeDto deletedEmployeeDTO = mapper.employeeToDto(employeeForDelete);
        log.debug("deleteEmployeeById() - end, record id = {} deleted", id);
        kafkaProducer.sendMessage("The record was deleted from " + kafkaProducer.createMessageForSending(deletedEmployeeDTO));
    }
}
