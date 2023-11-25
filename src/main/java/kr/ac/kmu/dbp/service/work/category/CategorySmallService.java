package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.*;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface CategorySmallService {
    public void create(Employee employee, CategorySmallDtoCreate categorySmallDtoCreate);
    public void update(Employee employee, CategorySmallDtoUpdate categorySmallDtoUpdate);
    public void delete(Employee employee, int pid);
    public List<CategorySmallDtoRead> readAll();
    public List<CategorySmallDtoRead> readByCategoryMediumPid(int categoryMediumPid);
    public CategorySmallDtoRead readByPid(int pid);
}
