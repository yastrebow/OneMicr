package ru.yastrebov.onemicr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yastrebov.onemicr.repository.EmlpoyeeRepository;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

        @Autowired
        public EmlpoyeeRepository employeeRepository;

}
