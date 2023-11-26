package kr.ac.kmu.dbp.repository.attendance;

import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface AttendanceRepository {
    public void create(Attendance attendance);
    public List<Attendance> readByEmployee(Employee employee);
    public boolean checkExistByEmployeeAndAttendanceDate(Attendance attendance);
}
