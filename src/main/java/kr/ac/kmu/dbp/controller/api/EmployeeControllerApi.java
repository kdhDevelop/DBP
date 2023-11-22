package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRead;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.employee.EmployeeService;
import kr.ac.kmu.dbp.service.employee.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeControllerApi {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeControllerApi(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeService = employeeServiceImpl;
    }

    @PostMapping("/employee")
    public void create(@RequestBody EmployeeDtoCreate employeeDtoCreate) {
        employeeService.create(employeeDtoCreate);
    }

    @GetMapping("/employee")
    public EmployeeDtoRead info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new EmployeeDtoRead(customUserDetails.getEmployee());
    }

    @PutMapping("/employee")
    public void update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody EmployeeDtoUpdate employeeDtoUpdate) {
        employeeService.update(customUserDetails.getEmployee().getPid(), employeeDtoUpdate);
    }

    @GetMapping("/employee/all")
    public List<Integer> readAllAccount() {
        return employeeService.readAllAccount();
    }

    @GetMapping(value = "/employee", params = "age")
    public List<EmployeeDtoRead> readByAge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String age) {
        return employeeService.readByAge(customUserDetails.getEmployee(), Integer.parseInt(age));
    }

    @GetMapping(value = "/employee", params = "name")
    public List<EmployeeDtoRead> readByName(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String name) {
        return employeeService.readByName(customUserDetails.getEmployee(), name);
    }

    @GetMapping(value = "/employee", params = "departmentPid")
    public List<EmployeeDtoRead> readByDepartmentPid(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String departmentPid) {
        return employeeService.readByDepartmentPid(customUserDetails.getEmployee(), Integer.parseInt(departmentPid));
    }
}
