package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.entity.department.Department;

import java.util.List;

public interface DepartmentRepository {
    public void create(Department department);
    public Department readByPid(int pid);
    public boolean checkExistByName(String name);
    public void delete(Department department);
    public void update(Department department);
    public List<Department> readAll();
}
