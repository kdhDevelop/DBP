package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.dto.department.DepartmentDtoUpdate;
import kr.ac.kmu.dbp.entity.department.Department;

import java.util.List;

public interface DepartmentService {

    public Department readByPid(int pid);
    public void create(DepartmentDtoCreate departmentDtoCreate);
    public void delete(int pid);
    public void update(DepartmentDtoUpdate departmentDtoUpdate);
    public List<Department> readAll();
}
