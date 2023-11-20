package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface CategoryMediumService {
    public void create(Employee employee, CategoryMediumDtoCreate categoryLargeDtoCreate);
    public void update(Employee employee, CategoryMediumDtoUpdate categoryLargeDtoUpdate);
    public void delete(Employee employee, int pid);
    public List<CategoryMediumDtoRead> readAll();
}
