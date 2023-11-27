package kr.ac.kmu.dbp.dto.attendance;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceWorkSalaryDtoRead {
    private int employeePid;

    private int year;
    private int month;

    private int workTime;
    private int workWage;

    private int overWorkTime;
    private double overWorkMultiple;
    private int overWorkWage;

    private int nightWorkTime;
    private double nightWorkMultiple;
    private int nightWorkWage;

    private int holidayWorkTime;
    private double holidayWorkMultiple;
    private int holidayWorkWage;

    private int totalSalary;

    private AttendanceSalaryPeeDtoRead attendanceSalaryPeeDtoRead;
}
