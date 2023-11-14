package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.employee.EmployeeService;
import kr.ac.kmu.dbp.service.employee.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeControllerApi {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeControllerApi(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeService = employeeServiceImpl;
    }
}
