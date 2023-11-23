package kr.ac.kmu.dbp.dto.attendance;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;

@Getter
public class AttendanceDtoCreate {
    private int employeePid;
    private Date attendanceDate;
    private DayOfWeek dayOfWeek;
    private Time startTime;
    private Time endTime;
}
