package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeServiceImpl(EmployeeDataBaseRepository employeeDataBaseRepository, DepartmentDataBaseRepository departmentDataBaseRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeDataBaseRepository;
        this.departmentRepository = departmentDataBaseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee readByPid(int pid) {
        return employeeRepository.readByPid(pid);
    }

    @Override
    public Employee readByAccount(String account) {
        return employeeRepository.readByAccount(account);
    }

    @Override
    public void create(EmployeeDtoCreate employeeDtoCreate) {
        Employee employee = new Employee(employeeDtoCreate);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setDepartment(departmentRepository.readByPid(employeeDtoCreate.getDepartmentPid()));

        employeeRepository.create(employee);
    }
}
