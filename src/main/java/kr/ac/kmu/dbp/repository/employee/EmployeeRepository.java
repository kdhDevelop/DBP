package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface EmployeeRepository {
    public Employee create(Employee employee);
    public Employee readByPid(int pid);
    public Employee readByAccount(String account);
    public List<Employee> readAll();
    public Employee update(Employee employee);
    public boolean checkExistByResidentRegistrationNumber(String residentRegistrationNumber);
}
