package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.entity.employee.Employee;

public interface EmployeeRepository {
    public Employee create(Employee employee);
}