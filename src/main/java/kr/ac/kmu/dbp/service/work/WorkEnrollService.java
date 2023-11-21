package kr.ac.kmu.dbp.service.work;

import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoCreate;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoRead;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.sql.Date;
import java.util.List;

public interface WorkEnrollService {
    public void create(Employee employee, WorkEnrollDtoCreate workEnrollDtoCreate);
    public void update(Employee employee, WorkEnrollDtoUpdate workEnrollDtoUpdate);
    public void delete(Employee employee, int pid);
    public List<WorkEnrollDtoRead> readAll(Employee employee, Date date);
}
