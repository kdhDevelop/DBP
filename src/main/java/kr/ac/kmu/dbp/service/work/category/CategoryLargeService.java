package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface CategoryLargeService {
    public void create(Employee employee, CategoryLargeDtoCreate categoryLargeDtoCreate);
}
