package kr.ac.kmu.dbp.service.department;

import kr.ac.kmu.dbp.entity.department.Department;

public interface DepartmentService {

    public Department readByPid(int pid);
}
