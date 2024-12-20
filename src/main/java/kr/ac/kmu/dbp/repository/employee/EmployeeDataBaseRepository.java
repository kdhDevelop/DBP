package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.entity.department.Department;
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
public class EmployeeDataBaseRepository extends Table implements EmployeeRepository {

    @Autowired
    public EmployeeDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "employee");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE employee ( pid int NOT NULL AUTO_INCREMENT, account varchar(100), password varchar(1000), name varchar(50), gender varchar(50), birthYear int, wage int, residentRegistrationNumber char(14) UNIQUE, phoneNumber char(14), zipCode int unsigned, address1 varchar(1000), address2 varchar(1000), role varchar(100), departmentPid int, rank varchar(100), PRIMARY KEY(pid) );";
    }

    @Override
    public void create(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO employee (password, name, gender, birthYear, wage, residentRegistrationNumber, phoneNumber, zipCode, address1, address2, role, departmentPid, rank) VALUES ('|=PASSWORD=|', '|=NAME=|', '|=GENDER=|', |=BIRTH_YEAR=|, |=WAGE=|, '|=RESIDENT_REGISTRATION_NUMBER=|', '|=PHONE_NUMBER=|', |=ZIP_CODE=|, '|=ADDRESS_1=|', '|=ADDRESS_2=|', '|=ROLE=|', |=DEPARTMENT_PID=|, '|=RANK=|');"
                            .replace("|=PASSWORD=|", employee.getPassword())
                            .replace("|=NAME=|", employee.getName())
                            .replace("|=GENDER=|", employee.getGender().toString())
                            .replace("|=BIRTH_YEAR=|", String.valueOf(employee.getBirthYear()))
                            .replace("|=WAGE=|", String.valueOf(employee.getWage()))
                            .replace("|=RESIDENT_REGISTRATION_NUMBER=|", employee.getResidentRegistrationNumber())
                            .replace("|=PHONE_NUMBER=|", employee.getPhoneNumber())
                            .replace("|=ZIP_CODE=|", String.valueOf(employee.getZipCode()))
                            .replace("|=ADDRESS_1=|", employee.getAddress1())
                            .replace("|=ADDRESS_2=|", employee.getAddress2())
                            .replace("|=ROLE=|", employee.getRole().name())
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(employee.getDepartment().getPid()))
                            .replace("|=RANK=|", employee.getRank().name());
                    System.out.println("CREATE QUERY : " + createQuery);
                    if (statement.executeUpdate(createQuery) > 0) {
                        String updateQuery = "";
                        String readQuery = "";
                        readQuery = "SELECT pid FROM employee WHERE residentRegistrationNumber = '|=RESIDENT_REGISTRATION_NUMBER=|'"
                                .replace("|=RESIDENT_REGISTRATION_NUMBER=|", employee.getResidentRegistrationNumber());
                        System.out.println("READ QUERY : " + readQuery);
                        try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                            if (resultSet.next()) {
                                int pid = resultSet.getInt("pid");
                                String account = "E" + String.format("%05d", pid);

                                updateQuery = "UPDATE employee SET account = '|=ACCOUNT=|' WHERE pid = '|=PID=|'"
                                        .replace("|=ACCOUNT=|", account)
                                        .replace("|=PID=|", String.valueOf(pid));
                                System.out.println("UPDATE QUERY : " + updateQuery);
                            }
                        }
                        statement.executeUpdate(updateQuery);
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Employee readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String findQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid AND emp.pid = '|=PID=|';"
                            .replace("|=PID=|", String.valueOf(pid));
                    System.out.println("READ EMPLOYEE QUERY : " + findQuery);
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new Employee(resultSet, "emp_", "dep_");
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Employee readByAccount(String account) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid AND emp.account = '|=ACCOUNT=|';"
                            .replace("|=ACCOUNT=|", account);
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        if (resultSet.next()) {
                            return new Employee(resultSet, "emp_", "dep_");
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Employee> readAll() {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Employee> result = new ArrayList<>();
                    String readQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid;";
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Employee(resultSet, "emp_", "dep_"));
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
    public List<Employee> readByBirthYear(int birthYear) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Employee> result = new ArrayList<>();
                    String readQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid AND emp.birthYear = |=BIRTH_YEAR=|;"
                            .replace("|=BIRTH_YEAR=|", String.valueOf(birthYear));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Employee(resultSet, "emp_", "dep_"));
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
    public List<Employee> readByName(String name) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Employee> result = new ArrayList<>();
                    String readQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid AND emp.name LIKE '%|=NAME=|%';"
                            .replace("|=NAME=|", name);
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Employee(resultSet, "emp_", "dep_"));
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
    public List<Employee> readByDepartment(Department department) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Employee> result = new ArrayList<>();
                    String readQuery = "SELECT emp.pid as emp_pid, emp.account as emp_account, emp.password as emp_password, emp.name as emp_name, emp.gender as emp_gender, emp.birthYear as emp_birthYear, emp.wage as emp_wage, emp.residentRegistrationNumber as emp_residentRegistrationNumber, emp.phoneNumber as emp_phoneNumber, emp.zipCode as emp_zipCode, emp.address1 as emp_address1, emp.address2 as emp_address2, emp.role as emp_role, emp.rank as emp_rank, dep.pid as dep_pid, dep.name as dep_name FROM employee as emp, department as dep WHERE emp.departmentPid = dep.pid AND dep.pid = |=DEPARTMENT_PID=|;"
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(department.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Employee(resultSet, "emp_", "dep_"));
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
    public void update(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE employee as emp SET name = '|=NAME=|', gender = '|=GENDER=|', birthYear = |=BIRTH_YEAR=|, wage = |=WAGE=|, phoneNumber = '|=PHONE_NUMBER=|', zipCode = |=ZIP_CODE=|, address1 = '|=ADDRESS1=|', address2 = '|=ADDRESS2=|', role = '|=ROLE=|', rank = '|=RANK=|', departmentPid = |=DEPARTMENT_PID=| WHERE emp.pid = |=PID=|;"
                            .replace("|=NAME=|", employee.getName())
                            .replace("|=GENDER=|", employee.getGender().name())
                            .replace("|=BIRTH_YEAR=|", String.valueOf(employee.getBirthYear()))
                            .replace("|=WAGE=|", String.valueOf(employee.getWage()))
                            .replace("|=PHONE_NUMBER=|", employee.getPhoneNumber())
                            .replace("|=ZIP_CODE=|", String.valueOf(employee.getZipCode()))
                            .replace("|=ADDRESS1=|", employee.getAddress1())
                            .replace("|=ADDRESS2=|", employee.getAddress2())
                            .replace("|=ROLE=|", employee.getRole().name())
                            .replace("|=RANK=|", employee.getRank().name())
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(employee.getDepartment().getPid()))
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("UPDATE QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteEmployeeQuery = "DELETE FROM employee WHERE pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("DELETE EMPLOYEE QUERY : " + deleteEmployeeQuery);
                    statement.executeUpdate(deleteEmployeeQuery);
                    String deleteApprovalQuery = "DELETE FROM approval WHERE drafterEmployeePid = |=PID=| OR firstApprovalEmployeePid = |=PID=| OR secondApprovalEmployeePid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("DELETE APPROVAL QUERY : " + deleteApprovalQuery);
                    statement.executeUpdate(deleteApprovalQuery);
                    String attendanceDeleteQuery = "DELETE FROM attendance WHERE employeePid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("DELETE ATTENDANCE QUERY : " + deleteApprovalQuery);
                    statement.executeUpdate(attendanceDeleteQuery);
                    String deleteMailQuery = "DELETE FROM mail WHERE senderPid = |=PID=| OR receiverPid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("DELETE MAIL QUERY : " + deleteMailQuery);
                    statement.executeUpdate(deleteMailQuery);
                    String deleteWorkEnrollQuery = "DELETE FROM workEnroll WHERE employeePid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("DELETE WORK ENROLL QUERY : " + deleteWorkEnrollQuery);
                    statement.executeUpdate(deleteWorkEnrollQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkExistByResidentRegistrationNumber(String residentRegistrationNumber) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String checkExistQuery = "SELECT * FROM employee WHERE residentRegistrationNumber = '|=RESIDENT_REGISTRATION_NUMBER=|';"
                            .replace("|=RESIDENT_REGISTRATION_NUMBER=|", residentRegistrationNumber);
                    System.out.println("CHECK EXIST QUERY : " + checkExistQuery);
                    try (ResultSet resultSet = statement.executeQuery(checkExistQuery)) {
                        return resultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
