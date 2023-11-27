package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.*;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface AttendanceService {
    public void create(Employee employee, AttendanceDtoCreate attendanceDtoCreate);
    public List<AttendanceDtoRead> readByEmployee(Employee reader, Employee target);
    public List<AttendanceDtoRead> readByEmployeeAndYearAndMonth(Employee employee, int year, int month);
    public AttendanceWorkSalaryDtoRead calculateWorkSalaryByEmployeeAndYearAndMonth(Employee employee, int year, int month, AttendanceWageMultiple attendanceWageMultiple);
    public List<AttendanceWorkSalaryDtoRead> calculateAllWorkSalaryByYearAndMonth(Employee employee, int year, int month, AttendanceWageMultiple attendanceWageMultiple);
}
