package ru.yastrebov.onemicr.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.mupstruct.EmployeeMapper;
import ru.yastrebov.onemicr.repository.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;

    public List<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(mapper::employeeToDto).collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(UUID id) {
        Employee getEmployee = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.employeeToDto(getEmployee);
    }

    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(mapper.dtoToEmployee(employeeDto));
        return mapper.employeeToDto(savedEmployee);
    }

    public EmployeeDto updateById(EmployeeDto employeeDto, UUID id) {

        //            log.info("Method updateEmployee in DefaultEmployeeServiceImpl update employee with id={}", id);
        final Employee updatedEmployee = employeeRepository.findById(id) //находим по id сущность для обновления
                .orElseThrow(EntityNotFoundException::new);

        mapper.updateFromDtoToEntity(employeeDto, updatedEmployee);//маппим ДТО в Эмплоии???
        final Employee savedEmployee = employeeRepository.save(updatedEmployee);//обновляемая сущность с сохраненными изменениями
        final EmployeeDto updatedEmployeeDto = mapper.employeeToDto(savedEmployee); //обновленная ДТО-шка

        return updatedEmployeeDto;
    }

    public void deleteEmployeeById(final UUID id) {
//            log.info("Method deleteEmployeeById in DefaultEmployeeServiceImpl delete project by id={}", id);

        final Employee employeeForDelete = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(employeeForDelete);

        final EmployeeDto deletedEmployeeDTO = mapper.employeeToDto(employeeForDelete);

//            sendMessage(createMessage(deletedEmployeeDTO, "deleted"));

    }
}
