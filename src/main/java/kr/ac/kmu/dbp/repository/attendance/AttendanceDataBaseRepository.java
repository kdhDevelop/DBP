package kr.ac.kmu.dbp.repository.attendance;

import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Attendance> readByEmployee(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Attendance> result = new ArrayList<>();
                    String readQuery = "SELECT atte.attendanceDate as atte_attendanceDate, atte.dayOfWeek as atte_dayOfWeek, atte.startTime as atte_startTime, atte.endTime as atte_endTime,  atte.wage as atte_wage, emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM  attendance as atte, employee as emp, department as dep WHERE atte.employeePid = emp.pid AND emp.departmentPid = dep.pid AND emp.pid = |=EMPLOYEE_PID=|;"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(employee.getPid()));
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Attendance(resultSet, "atte_", "emp_", "dep_"));
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkExistByEmployeeAndAttendanceDate(Attendance attendance) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String checkQuery = "SELECT * FROM attendance WHERE employeePid = |=EMPLOYEE_PID=| AND attendanceDate = '|=ATTENDANCE_DATE=|';"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(attendance.getEmployee().getPid()))
                            .replace("|=ATTENDANCE_DATE=|", attendance.getAttendanceDate().toString());

                    System.out.println("CHECK EXIST ATTENDANCE QUERY : " + checkQuery);
                    try (ResultSet resultSet = statement.executeQuery(checkQuery)) {
                        return resultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
