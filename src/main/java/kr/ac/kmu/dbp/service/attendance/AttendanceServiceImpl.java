package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.attendance.AttendanceDataBaseRepository;
import kr.ac.kmu.dbp.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceDataBaseRepository attendanceDataBaseRepository) {
        this.attendanceRepository = attendanceDataBaseRepository;
    }

    @Override
    public void create(Employee creator, Employee target, AttendanceDtoCreate attendanceDtoCreate) {
        if (creator.getRole() == Role.부서장 || creator.getRole() == Role.사장 || creator.getPid() == target.getPid()) {
            Attendance attendance = Attendance.builder()
                    .employee(target)
                    .attendanceDate(attendanceDtoCreate.getAttendanceDate())
                    .dayOfWeek(DayOfWeek.valueOf(attendanceDtoCreate.getDayOfWeek()))
                    .startTime(attendanceDtoCreate.getStartTime())
                    .endTime(attendanceDtoCreate.getEndTime())
                    .wage(target.getWage())
                    .build();
            attendanceRepository.create(attendance);
        }
    }
}
