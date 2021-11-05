package ru.yastrebov.onemicr.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.mupstruct.EmployeeMapper;
import ru.yastrebov.onemicr.repository.EmlpoyeeRepository;
import ru.yastrebov.onemicr.service.EmployeeService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmlpoyeeRepository employeeRepository;
    private final EmployeeMapper mapper;

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()//создали из листа стрим
                .map(mapper::employeeToEmployeeDto).collect(Collectors.toList());
        //оператором из streamAPI map, использовали для каждого элемента метод employeeToEmployeeDto из класса EmployeeMapper
        //и превратили стрим обратно в лист
    }

    @Override
    public EmployeeDto getEmployeeById(UUID id) {
        final Employee getEmployee = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.employeeToEmployeeDto(getEmployee); //getById() - не нужен?
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(mapper.employeeDtoToEmployee(employeeDto));
        return mapper.employeeToEmployeeDto(savedEmployee); //save() - ???
    }

    @Override
    public EmployeeDto updateById(final EmployeeDto employeeDTO, final UUID id) {
//            log.info("Method updateEmployee in DefaultEmployeeServiceImpl update employee with id={}", id);
        final Employee updatedEmployee = employeeRepository.findById(id) //находим по id сущность для обновления
                .orElseThrow(EntityNotFoundException::new);

        mapper.updateFromDtoToEntity(employeeDTO, updatedEmployee);//маппим ДТО в Эмплоии???
        final Employee savedEmployee = employeeRepository.save(updatedEmployee);//обновляемая сущность с сохраненными изменениями
        final EmployeeDto updatedEmployeeDTO = mapper.employeeToEmployeeDto(savedEmployee); //обновленная ДТО-шка

        return updatedEmployeeDTO;
    }

    @Override
    public void deleteEmployeeById(final UUID id) {
//            log.info("Method deleteEmployeeById in DefaultEmployeeServiceImpl delete project by id={}", id);

        final Employee employeeForDelete = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(employeeForDelete);

        final EmployeeDto deletedEmployeeDTO = mapper.employeeToEmployeeDto(employeeForDelete);

//            sendMessage(createMessage(deletedEmployeeDTO, "deleted"));

    }
}
