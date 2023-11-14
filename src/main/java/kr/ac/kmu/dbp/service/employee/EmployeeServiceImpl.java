package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.employeeRepository = employeeDataBaseRepository;
    }

    @Override
    public Employee readByPid(int pid) {
        return employeeRepository.readByPid(pid);
    }

    @Override
    public Employee readByAccount(String account) {
        return employeeRepository.readByAccount(account);
    }
}
