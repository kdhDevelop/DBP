package kr.ac.kmu.dbp.repository.attendance;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttendanceDataBaseRepository extends Table implements AttendanceRepository {

    @Autowired
    public AttendanceDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "attendance");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE attendance ( employeePid int, attendanceDate date, dayOfWeek varchar(10), startTime time, endTime time, wage int, PRIMARY KEY(employeePid, attendanceDate) );";
    }
}
