package kr.ac.kmu.dbp.entity.work;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.*;

@Data
@Builder
@AllArgsConstructor
public class WorkEnroll {
    private int pid;
    private Employee employee;
    private Date workDate;
    private CategorySmall categorySmall;
    private Time startWork;
    private Time endWork;

    public WorkEnroll(ResultSet resultSet, String workPrefix, String employeePrefix, String departmentPrefix, String categorySmallPrefix, String categoryMediumPrefix, String categoryLargePrefix) throws SQLException {
        this.pid = resultSet.getInt(workPrefix + "pid");
        this.employee = new Employee(resultSet, employeePrefix, departmentPrefix);
        this.workDate = resultSet.getDate(workPrefix + "workDate");
        this.categorySmall = new CategorySmall(resultSet, categorySmallPrefix, categoryMediumPrefix, categoryLargePrefix);
        this.startWork = resultSet.getTime(workPrefix + "startWork");
        this.endWork = resultSet.getTime(workPrefix + "endWork");
    }
}
