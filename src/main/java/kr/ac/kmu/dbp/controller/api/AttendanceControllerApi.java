package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoRead;
import kr.ac.kmu.dbp.dto.attendance.AttendanceWageMultiple;
import kr.ac.kmu.dbp.dto.attendance.AttendanceWorkSalaryDtoRead;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.attendance.AttendanceService;
import kr.ac.kmu.dbp.service.attendance.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AttendanceControllerApi {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceControllerApi(AttendanceServiceImpl attendanceServiceImpl) {
        this.attendanceService = attendanceServiceImpl;
    }

    @PostMapping("/attendance")
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody AttendanceDtoCreate attendanceDtoCreate) {
        attendanceService.create(customUserDetails.getEmployee(), attendanceDtoCreate);
    }

    @GetMapping("/attendance")
    public List<AttendanceDtoRead> readByYearAndMonth(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String year, @RequestParam String month) {
        return attendanceService.readByEmployeeAndYearAndMonth(customUserDetails.getEmployee(), Integer.parseInt(year), Integer.parseInt(month));
    }

    @GetMapping("/attendance/salary")
    public AttendanceWorkSalaryDtoRead calculateSalaryByEmployeeAndYearAndMonthAndMultiple(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String year, @RequestParam String month, @RequestParam String overWorkMultiple, @RequestParam String nightWorkMultiple, @RequestParam String holidayWorkMultiple) {
        return attendanceService.calculateWorkSalaryByEmployeeAndYearAndMonth(customUserDetails.getEmployee(), Integer.parseInt(year), Integer.parseInt(month), new AttendanceWageMultiple(Double.parseDouble(overWorkMultiple), Double.parseDouble(nightWorkMultiple), Double.parseDouble(holidayWorkMultiple)));
    }

}
