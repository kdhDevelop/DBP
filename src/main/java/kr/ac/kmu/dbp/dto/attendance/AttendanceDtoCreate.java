package kr.ac.kmu.dbp.dto.attendance;

import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

@Getter
public class AttendanceDtoCreate {
    private Date attendanceDate;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
}
