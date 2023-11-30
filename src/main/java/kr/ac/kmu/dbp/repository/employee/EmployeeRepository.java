package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface EmployeeRepository {
    public void create(Employee employee);
    public Employee readByPid(int pid);
    public Employee readByAccount(String account);
    public List<Employee> readAll();
    public List<Employee> readByBirthYear(int birthYear);
    public List<Employee> readByName(String name);
    public List<Employee> readByDepartment(Department department);
    public void update(Employee employee);
    public void delete(Employee employee);
    public boolean checkExistByResidentRegistrationNumber(String residentRegistrationNumber);
}
