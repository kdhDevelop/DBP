package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.attendance.AttendanceService;
import kr.ac.kmu.dbp.service.attendance.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AttendanceControllerApi {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceControllerApi(AttendanceServiceImpl attendanceServiceImpl) {
        this.attendanceService = attendanceServiceImpl;
    }
}
