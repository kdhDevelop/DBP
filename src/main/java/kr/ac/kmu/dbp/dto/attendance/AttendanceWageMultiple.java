package kr.ac.kmu.dbp.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendanceWageMultiple {
    private double overWorkMultiple;
    private double nightWorkMultiple;
    private double holidayWorkMultiple;
}
