package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.entity.department.Department;

public interface DepartmentRepository {
    public Department create(Department department);
    public Department readByPid(int pid);
    public boolean checkExistByName(String name);
    public void delete(Department department);
}
