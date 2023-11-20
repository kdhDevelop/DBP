package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface CategoryLargeService {
    public void create(Employee employee, CategoryLargeDtoCreate categoryLargeDtoCreate);
    public void update(Employee employee, CategoryLargeDtoUpdate categoryLargeDtoUpdate);
    public void delete(Employee employee, int pid);
}
