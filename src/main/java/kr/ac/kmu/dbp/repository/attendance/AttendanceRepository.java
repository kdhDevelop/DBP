package kr.ac.kmu.dbp.repository.attendance;

import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository {
    public void create(Attendance attendance);
    public List<Attendance> readByEmployee(Employee employee);
    public boolean checkExistByEmployeeAndAttendanceDate(Attendance attendance);
    public List<Attendance> readByEmployeeAndBetweenAttendanceDate(Employee employee, Date startDate, Date endDate);
}
