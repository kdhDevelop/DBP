package kr.ac.kmu.dbp.repository.attendance;

import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public void create(Attendance attendance) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (|=EMPLOYEE_PID=|, '|=ATTENDANCE_DATE=|', '|=DAY_OF_WEEK=|', '|=START_TIME=|', '|=END_TIME=|', |=WAGE=|);"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(attendance.getEmployee().getPid()))
                            .replace("|=ATTENDANCE_DATE=|", attendance.getAttendanceDate().toString())
                            .replace("|=DAY_OF_WEEK=|", attendance.getDayOfWeek().toString())
                            .replace("|=START_TIME=|", attendance.getStartTime().toString())
                            .replace("|=END_TIME=|", attendance.getEndTime().toString())
                            .replace("|=WAGE=|", String.valueOf(attendance.getWage()));
                    System.out.println("CREATE ATTENDANCE QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
