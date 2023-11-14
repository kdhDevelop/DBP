package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoRepository;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRepository;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Gender;
import kr.ac.kmu.dbp.entity.employee.Rank;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentDataBaseRepository departmentDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.departmentRepository = departmentDataBaseRepository;
    }

    @Override
    public Department readByPid(int pid) {
        return departmentRepository.readByPid(pid);
    }
}
