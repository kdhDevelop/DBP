package kr.ac.kmu.dbp.dto.attendance;

import kr.ac.kmu.dbp.entity.attendance.Attendance;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class AttendanceDtoRead {
    private int employeePid;
    private Date attendanceDate;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private int wage;

    public AttendanceDtoRead(Attendance attendance) {
        this. employeePid = attendance.getEmployee().getPid();
        this.attendanceDate = attendance.getAttendanceDate();
        this.dayOfWeek = attendance.getDayOfWeek().toString();
        this.startTime = attendance.getStartTime();
        this.endTime = attendance.getEndTime();
        this.wage = attendance.getWage();
    }
}
