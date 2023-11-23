package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface AttendanceService {
    public void create(Employee employee, AttendanceDtoCreate attendanceDtoCreate);
}
