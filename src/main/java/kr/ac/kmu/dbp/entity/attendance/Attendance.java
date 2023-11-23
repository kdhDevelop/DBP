package kr.ac.kmu.dbp.entity.attendance;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Attendance {
    private Employee employee;
    private Date attendanceDate;
    private DayOfWeek dayOfWeek;
    private Time startTime;
    private Time endTime;
    private int wage;

    public Attendance(ResultSet resultSet, String attendancePrefix, String employeePrefix, String departmentPrefix) throws SQLException {
        this.employee = new Employee(resultSet, employeePrefix, departmentPrefix);
        this.attendanceDate = resultSet.getDate(attendancePrefix + "attendanceDate");
        this.dayOfWeek = DayOfWeek.valueOf(resultSet.getString(attendancePrefix + "dayOfWeek"));
        this.startTime = resultSet.getTime(attendancePrefix + "startTime");
        this.endTime = resultSet.getTime(attendancePrefix + "endTime");
        this.wage = resultSet.getInt(attendancePrefix + "wage");
    }
}
