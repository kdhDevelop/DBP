package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface EmployeeService {
    public Employee readByPid(int pid);

    public Employee readByAccount(String account);

    public void create(EmployeeDtoCreate employeeDtoCreate);
}
