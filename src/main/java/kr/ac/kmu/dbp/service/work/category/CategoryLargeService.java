package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface CategoryLargeService {
    public void create(Employee employee, CategoryLargeDtoCreate categoryLargeDtoCreate);
    public void update(Employee employee, CategoryLargeDtoUpdate categoryLargeDtoUpdate);
    public void delete(Employee employee, int pid);
    public List<CategoryLargeDtoRead> readAll();
    public CategoryLargeDtoRead readByPid(int pid);
}
