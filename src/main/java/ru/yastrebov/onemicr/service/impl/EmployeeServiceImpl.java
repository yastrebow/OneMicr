package ru.yastrebov.onemicr.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.yastrebov.onemicr.dto.EmployeeDto;
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
    private final KafkaTemplate<String, String> kafkaTemplate;

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
        Employee savedEmployee = employeeRepository.save(mapper.dtoToEmployee(employeeDto));

        String messageAboutCreating = String.format("New record was created into Employee DB with those values: id = %s, firstName = %s, lastName = %s, age = %d, " +
                        "experience = %f, position = %s, project = %s, hireDate = %s, gender = %s",
                employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getAge(), employeeDto.getExperience(),
                employeeDto.getPosition().toString(), employeeDto.getProject().toString(), employeeDto.getHireDate().toString(), employeeDto.getGender().toString());

        sendMessage(messageAboutCreating);
        return mapper.employeeToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateById(EmployeeDto employeeDto, UUID id) {

        final Employee updatedEmployee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        mapper.updateFromDtoToEntity(employeeDto, updatedEmployee);
        final Employee savedEmployee = employeeRepository.save(updatedEmployee);

        String messageAboutUpdating = String.format("This record was updated into Employee DB with those values: id = %s, firstName = %s, lastName = %s, age = %d, " +
                        "experience = %f, position = %s, project = %s, hireDate = %s, gender = %s",
                employeeDto.getId(), employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getAge(), employeeDto.getExperience(),
                employeeDto.getPosition().toString(), employeeDto.getProject().toString(), employeeDto.getHireDate().toString(), employeeDto.getGender().toString());

        sendMessage(messageAboutUpdating);

        return mapper.employeeToDto(savedEmployee);

    }

    @Override
    public void deleteEmployeeById(final UUID id) {

        final Employee employeeForDelete = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(employeeForDelete);

        final EmployeeDto deletedEmployeeDTO = mapper.employeeToDto(employeeForDelete);

        String messageAboutDeleting = String.format("The record with was deleted from Employee DB with those values: id = %s, firstName = %s, lastName = %s, age = %d, " +
                        "experience = %f, position = %s, project = %s, hireDate = %s, gender = %s",
                employeeForDelete.getId(), employeeForDelete.getFirstName(), employeeForDelete.getLastName(), employeeForDelete.getAge(), employeeForDelete.getExperience(),
                employeeForDelete.getPosition().toString(), employeeForDelete.getProject().toString(), employeeForDelete.getHireDate().toString(), employeeForDelete.getGender().toString());

        sendMessage(messageAboutDeleting);

    }

    @Override
    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send("employeeDB", message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable exception) {
                System.out.println("Unable to sent message=["
                        + message + "] due to: " + exception.getMessage());
            }
        });
    }
}
