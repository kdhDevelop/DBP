package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoDataBaseRepository;
import kr.ac.kmu.dbp.entity.department.Department;

public interface DepartmentRepository {
    public DepartmentDtoDataBaseRepository create(Department department);
    public DepartmentDtoDataBaseRepository readByPid(int pid);
}
