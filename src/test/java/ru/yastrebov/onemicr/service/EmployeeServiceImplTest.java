package ru.yastrebov.onemicr.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.kafka.KafkaProducer;
import ru.yastrebov.onemicr.model.Employee;
import ru.yastrebov.onemicr.model.enums.Gender;
import ru.yastrebov.onemicr.model.enums.Position;
import ru.yastrebov.onemicr.model.enums.Project;
import ru.yastrebov.onemicr.mupstruct.EmployeeMapper;
import ru.yastrebov.onemicr.repository.EmployeeRepository;
import ru.yastrebov.onemicr.service.impl.EmployeeServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    private final UUID id1 = UUID.randomUUID();
    private final UUID id2 = UUID.randomUUID();

    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeMapper mapper;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    @Spy
    private EmployeeServiceImpl service;

    private final Employee employee1 = Employee.builder()
            .id(id1)
            .firstName("Bruce")
            .lastName("Willis")
            .age(65)
            .experience(45.0)
            .position(Position.DIRECTOR)
            .project(Project.PROJECT1)
            .hireDate(LocalDate.now())
            .gender(Gender.MALE)
            .build();

    private final Employee employee2 = Employee.builder()
            .id(id2)
            .firstName("Demi")
            .lastName("Moore")
            .age(62)
            .experience(42.0)
            .position(Position.MANAGER)
            .project(Project.PROJECT2)
            .hireDate(LocalDate.now())
            .gender(Gender.FEMALE)
            .build();
    private final EmployeeDto dto1 = EmployeeDto.builder()
            .id(id1)
            .firstName("Bruce")
            .lastName("Willis")
            .age(65)
            .experience(45.0)
            .position(Position.DIRECTOR)
            .project(Project.PROJECT1)
            .hireDate(LocalDate.now())
            .gender(Gender.MALE)
            .build();
    private final EmployeeDto dto2 = EmployeeDto.builder()
            .id(id2)
            .firstName("Demi")
            .lastName("Moore")
            .age(62)
            .experience(42.0)
            .position(Position.MANAGER)
            .project(Project.PROJECT2)
            .hireDate(LocalDate.now())
            .gender(Gender.FEMALE)
            .build();

    @Before
    public void setUp() {
        when(mapper.employeeToDto(employee1)).thenReturn(dto1);
    }

    @Test
    public void getAllWorkCorrectlyTest() {
        List<Employee> employeeList = List.of(employee1, employee2);

        when(repository.findAll()).thenReturn(employeeList);
        when(mapper.employeeToDto(employee2)).thenReturn(dto2);

        List<EmployeeDto> getAllList = service.getAll();

        assertNotNull(getAllList);
        assertEquals(employeeList.get(0).getAge(), getAllList.get(0).getAge());
        verify(repository, times(1)).findAll();
        verify(mapper, times(employeeList.size())).employeeToDto(any());
    }

    @Test
    public void getEmployeeDtoByIdWorkCorrectlyTest() {
        when(repository.findById(id1)).thenReturn(Optional.of(employee1));

        EmployeeDto getEmployeeDtoById = service.getEmployeeById(id1);

        assertNotNull(getEmployeeDtoById);
        assertEquals(employee1.getLastName(), getEmployeeDtoById.getLastName());
        verify(repository, times(1)).findById(id1);
    }

    @Test
    public void createDtoWorkCorrectlyTest() {
        when(repository.save(employee1)).thenReturn(employee1);
        when(mapper.dtoToEmployee(dto1)).thenReturn(employee1);
        when(kafkaProducer.sendMessage(any())).thenReturn(any(), ("String by matcher"));

        EmployeeDto createdDto = service.create(dto1);

        assertNotNull(createdDto);
        assertEquals(employee1.getFirstName(), createdDto.getFirstName());
        verify(repository, times(1)).save(employee1);
    }

    @Test
    public void updateByIdReturnDtoWorkCorrectlyTest() {
        when(repository.findById(id1)).thenReturn(Optional.of(employee1));
        when(repository.save(employee1)).thenReturn(employee1);
        when(kafkaProducer.sendMessage(any())).thenReturn(any(), eq("String by matcher"));

        EmployeeDto updatedDtoById = service.updateById(dto1, id1);

        assertNotNull(updatedDtoById);
        assertEquals(employee1.getFirstName(), updatedDtoById.getFirstName());
        verify(repository, times(1)).save(employee1);
    }

    @Test
    public void deleteEmployeeByIdWorkCorrectlyTest() {
        when((repository.findById(id1))).thenReturn(Optional.of(employee1));
        when(kafkaProducer.sendMessage(any())).thenReturn(any(), ("String by matcher"));

        service.deleteEmployeeById(id1);
        verify(repository, times(1)).findById(id1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getEmployeeDtoByIdEntityNotFoundTest() {
        when(repository.findById(any())).thenThrow(new EntityNotFoundException());
        service.getEmployeeById(UUID.randomUUID());
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdShouldEntityNotFoundTest() {
        when(repository.findById(any())).thenThrow(new EntityNotFoundException());
        service.getEmployeeById(UUID.randomUUID());

    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteShouldEntityNotFoundTest() {
        when(repository.findById(any())).thenThrow(new EntityNotFoundException());
        service.deleteEmployeeById(UUID.randomUUID());
    }
}