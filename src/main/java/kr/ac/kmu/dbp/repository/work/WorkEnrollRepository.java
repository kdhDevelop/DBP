package kr.ac.kmu.dbp.repository.work;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.WorkEnroll;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;

import java.util.Date;
import java.util.List;

public interface WorkEnrollRepository {
    public void create(WorkEnroll workEnroll);
    public void update(WorkEnroll workEnroll);
    public void delete(WorkEnroll workEnroll);
    public List<WorkEnroll> readByEmployeeAndDateOrderByStartWork(Employee employee, Date date);
    public List<WorkEnroll> readByDateAndCategorySmall(Date date, CategorySmall categorySmall);
    public List<WorkEnroll> readByEmployee(Employee employee);
}
