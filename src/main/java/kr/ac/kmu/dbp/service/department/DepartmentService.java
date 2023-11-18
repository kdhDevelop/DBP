package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.dto.department.DepartmentDtoDelete;
import kr.ac.kmu.dbp.entity.department.Department;

public interface DepartmentService {

    public Department readByPid(int pid);
    public void create(DepartmentDtoCreate departmentDtoCreate);
    public void delete(DepartmentDtoDelete departmentDtoDelete);
}
