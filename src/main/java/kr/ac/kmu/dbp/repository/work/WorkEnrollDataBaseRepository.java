package kr.ac.kmu.dbp.repository.work;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.WorkEnroll;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WorkEnrollDataBaseRepository extends Table implements WorkEnrollRepository {

    @Autowired
    public WorkEnrollDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "workEnroll");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE workEnroll ( pid int NOT NULL UNIQUE AUTO_INCREMENT, employeePid int, workDate date, categorySmallPid int, startWork time, endWork time, PRIMARY KEY(pid) );";
    }

    @Override
    public void create(WorkEnroll workEnroll) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (|=EMPLOYEE_PID=|, '|=WORK_DATE=|', |=CATEGORY_SMALL_PID=|, '|=START_WORK=|', '|=END_WORK=|');"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(workEnroll.getEmployee().getPid()))
                            .replace("|=WORK_DATE=|", workEnroll.getWorkDate().toString())
                            .replace("|=CATEGORY_SMALL_PID=|", String.valueOf(workEnroll.getCategorySmall().getPid()))
                            .replace("|=START_WORK=|", workEnroll.getStartWork().toString())
                            .replace("|=END_WORK=|", workEnroll.getEndWork().toString());
                    System.out.println("CREATE QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(WorkEnroll workEnroll) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE workEnroll SET workDate = '|=WORK_DATE=|', categorySmallPid = |=CATEGORY_SMALL_PID=|, startWork = '|=START_WORK=|', endWork = '|=END_WORK=|' WHERE pid = |=PID=|;"
                            .replace("|=WORK_DATE=|", workEnroll.getWorkDate().toString())
                            .replace("|=CATEGORY_SMALL_PID=|", String.valueOf(workEnroll.getCategorySmall().getPid()))
                            .replace("|=START_WORK=|", workEnroll.getStartWork().toString())
                            .replace("|=END_WORK=|", workEnroll.getEndWork().toString())
                            .replace("|=PID=|", String.valueOf(workEnroll.getPid()));
                    System.out.println("UPDATE QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(WorkEnroll workEnroll) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "DELETE FROM workEnroll WHERE pid = |=PID=|"
                            .replace("|=PID=|", String.valueOf(workEnroll.getPid()));
                    System.out.println("DELETE QUERY : " + deleteQuery);
                    statement.executeUpdate(deleteQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<WorkEnroll> readByEmployeeAndDateOrderByStartWork(Employee employee, Date date) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT workE.pid as workE_pid, workE.workDate as workE_workDate, workE.startWork as workE_startWork, workE.endWork as workE_endWork, emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name, catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM workEnroll as workE, employee as emp, department as dep, categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid = catLarge.pid  AND emp.departmentPid = dep.pid  AND workE.employeePid = emp.pid AND workE.categorySmallPid = catSmall.pid AND emp.pid = |=EMPLOYEE_PID=| AND workE.workDate = '|=WORK_DATE=|' ORDER BY workE.startWork;"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(employee.getPid()))
                            .replace("|=WORK_DATE=|", date.toString());
                    System.out.println("READ QUERY : " + readQuery);
                    List<WorkEnroll> result = new ArrayList<>();
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new WorkEnroll(resultSet, "workE_", "emp_", "dep_", "catSmall_", "catMedium_", "catLarge_"));
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
    public List<WorkEnroll> readByDateAndCategorySmall(Date date, CategorySmall categorySmall) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT workE.pid as workE_pid, workE.workDate as workE_workDate, workE.startWork as workE_startWork, workE.endWork as workE_endWork, emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name, catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM workEnroll as workE, employee as emp, department as dep, categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid = catLarge.pid  AND emp.departmentPid = dep.pid  AND workE.employeePid = emp.pid AND workE.categorySmallPid = catSmall.pid AND workE.categorySmallPid = |=CATEGORY_SMALL_PID=| AND workE.workDate = '|=WORK_DATE=|' ORDER BY workE.startWork;"
                            .replace("|=CATEGORY_SMALL_PID=|", String.valueOf(categorySmall.getPid()))
                            .replace("|=WORK_DATE=|", date.toString());
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        List<WorkEnroll> result = new ArrayList<>();
                        while (resultSet.next()) {
                            result.add(new WorkEnroll(resultSet, "workE_", "emp_", "dep_", "catSmall_", "catMedium_", "catLarge_"));
                        }
                        return result;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<WorkEnroll> readByEmployee(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT workE.pid as workE_pid, workE.workDate as workE_workDate, workE.startWork as workE_startWork, workE.endWork as workE_endWork, emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name, catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM workEnroll as workE, employee as emp, department as dep, categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid = catLarge.pid  AND emp.departmentPid = dep.pid  AND workE.employeePid = emp.pid AND workE.categorySmallPid = catSmall.pid AND emp.pid = |=EMPLOYEE_PID=| ORDER BY workE.startWork;"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(employee.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        List<WorkEnroll> result = new ArrayList<>();
                        while (resultSet.next()) {
                            result.add(new WorkEnroll(resultSet, "workE_", "emp_", "dep_", "catSmall_", "catMedium_", "catLarge_"));
                        }
                        return result;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
