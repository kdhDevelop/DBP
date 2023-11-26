package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoRead;
import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.attendance.AttendanceDataBaseRepository;
import kr.ac.kmu.dbp.repository.attendance.AttendanceRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceDataBaseRepository attendanceDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.attendanceRepository = attendanceDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;

        init();
    }

    private void init() {

        Employee[] employees = new Employee[14];
        for (int T = 0 ; T < 13 ; T ++) {
            employees[T] = employeeRepository.readByPid(T+1);
        }

        LocalDate startDate = LocalDate.of(2023, 10, 15);
        LocalTime startTime = LocalTime.of(9, 0, 0);
        for (int T = 0 ; T < 32 ; T ++) {
            LocalDate currDate = startDate.plusDays(T);
            if (currDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            for (int TI = 0 ; TI < 13 ; TI ++) {
                Employee employee = employees[TI];
                int randomHour = (int) (Math.random() * 7);
                LocalTime currTime = startTime.plusHours(8 + randomHour);
                Attendance attendance = Attendance.builder()
                        .employee(employee)
                        .attendanceDate(Date.valueOf(currDate))
                        .dayOfWeek(currDate.getDayOfWeek())
                        .startTime(Time.valueOf(startTime))
                        .endTime(Time.valueOf(currTime))
                        .wage(employee.getWage())
                        .build();
                if (!attendanceRepository.checkExistByEmployeeAndAttendanceDate(attendance)) {
                    attendanceRepository.create(attendance);
                }
            }
        }




        for (int T = 1 ; T < 14 ; T ++) {
            Employee employee = employeeRepository.readByPid(T);
            for (int TI = 0 ; TI < 31 ; TI ++) {

            }
        }
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
