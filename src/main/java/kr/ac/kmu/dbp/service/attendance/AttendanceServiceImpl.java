package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoRead;
import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.attendance.AttendanceDataBaseRepository;
import kr.ac.kmu.dbp.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

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
                .dayOfWeek(DayOfWeek.valueOf(attendanceDtoCreate.getDayOfWeek()))
                .startTime(attendanceDtoCreate.getStartTime())
                .endTime(attendanceDtoCreate.getEndTime())
                .wage(employee.getWage())
                .build();
        attendanceRepository.create(attendance);
    }

    @Override
    public List<AttendanceDtoRead> readByEmployee(Employee reader, Employee target) {
        if (reader.getRole() == Role.부서장 || reader.getRole() == Role.사장 || reader.getPid() == target.getPid()) {
            List<AttendanceDtoRead> result = new ArrayList<>();
            List<Attendance> attendanceList = attendanceRepository.readByEmployee(target);
            for (Attendance attendance : attendanceList) {
                result.add(new AttendanceDtoRead(attendance));
            }
            return result;
        } else {
            throw new RuntimeException();
        }
    }
}
