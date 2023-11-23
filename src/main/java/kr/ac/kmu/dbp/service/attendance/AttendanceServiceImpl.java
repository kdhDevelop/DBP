package kr.ac.kmu.dbp.service.attendance;

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
}
