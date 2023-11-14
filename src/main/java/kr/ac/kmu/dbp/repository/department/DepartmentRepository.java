package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoRepository;
import kr.ac.kmu.dbp.entity.department.Department;

public interface DepartmentRepository {
    public Department create(Department department);
    public DepartmentDtoRepository readByPid(int pid);
}
