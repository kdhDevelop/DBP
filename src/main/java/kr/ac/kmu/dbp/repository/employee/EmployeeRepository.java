package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRepository;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface EmployeeRepository {
    public EmployeeDtoRepository create(Employee employee);
    public EmployeeDtoRepository readByPid(int pid);
    public EmployeeDtoRepository readByAccount(String account);
}
