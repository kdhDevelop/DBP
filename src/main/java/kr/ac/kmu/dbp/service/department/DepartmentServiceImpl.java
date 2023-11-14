package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
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

    @Override
    public void create(DepartmentDtoCreate departmentDtoCreate) {
        departmentRepository.create(new Department(departmentDtoCreate));
    }
}
