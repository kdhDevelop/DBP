package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentDataBaseRepository departmentDataBaseRepository) {
        this.departmentRepository = departmentDataBaseRepository;
    }

    @Override
    public Department readByPid(int pid) {
        return departmentRepository.readByPid(pid);
    }
}
