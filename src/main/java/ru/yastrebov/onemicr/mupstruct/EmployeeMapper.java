package ru.yastrebov.onemicr.mupstruct;

import org.mapstruct.*;
import ru.yastrebov.onemicr.dto.EmployeeDto;
import ru.yastrebov.onemicr.model.Employee;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface EmployeeMapper {

    EmployeeDto employeeToDto (Employee employee);

    Employee dtoToEmployee (EmployeeDto employeeDto);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromDtoToEntity(EmployeeDto employeeDto, @MappingTarget Employee employee);

}
