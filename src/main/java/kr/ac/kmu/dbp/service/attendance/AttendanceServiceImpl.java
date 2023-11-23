package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.attendance.AttendanceDataBaseRepository;
import kr.ac.kmu.dbp.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceDataBaseRepository attendanceDataBaseRepository) {
        this.attendanceRepository = attendanceDataBaseRepository;
    }

    @Override
    public void create(Employee employee, AttendanceDtoCreate attendanceDtoCreate) {
        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(attendanceDtoCreate.getAttendanceDate())
                .startTime(attendanceDtoCreate.getStartTime())
                .endTime(attendanceDtoCreate.getEndTime())
                .wage(employee.getWage())
                .build();
        attendanceRepository.create(attendance);
    }
}
