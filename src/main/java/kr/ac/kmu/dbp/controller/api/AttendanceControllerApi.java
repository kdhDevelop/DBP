package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoCreate;
import kr.ac.kmu.dbp.dto.attendance.AttendanceDtoRead;
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
    public List<AttendanceDtoRead> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return attendanceService.readByEmployee(customUserDetails.getEmployee(), customUserDetails.getEmployee());
    }
}
