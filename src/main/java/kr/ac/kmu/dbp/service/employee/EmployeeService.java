package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRead;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface EmployeeService {
    public Employee readByPid(int pid);

    public Employee readByAccount(String account);

    public List<Integer> readAllAccount();
    public List<EmployeeDtoRead> readByAge(Employee employee, int age);

    public void create(EmployeeDtoCreate employeeDtoCreate);

    public void update(int pid, EmployeeDtoUpdate employeeDtoUpdate);
}
