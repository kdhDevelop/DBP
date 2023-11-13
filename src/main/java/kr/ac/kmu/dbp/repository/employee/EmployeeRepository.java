package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoDataBaseRepository;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface EmployeeRepository {
    public EmployeeDtoDataBaseRepository create(Employee employee);
    public Employee readByPid(int pid);
}
