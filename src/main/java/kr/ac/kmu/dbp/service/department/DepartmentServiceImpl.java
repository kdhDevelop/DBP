package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentDataBaseRepository departmentDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.departmentRepository = departmentDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;
    }
}
