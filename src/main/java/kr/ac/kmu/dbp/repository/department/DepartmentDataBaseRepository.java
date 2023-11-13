package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoDataBaseRepository;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Component
public class DepartmentDataBaseRepository extends Table implements DepartmentRepository {

    @Autowired
    public DepartmentDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "department");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE department ( pid int NOT NULL AUTO_INCREMENT, name varchar(500) UNIQUE, departmentHeadPid int, PRIMARY KEY(pid) );";
    }

    @Override
    public DepartmentDtoDataBaseRepository create(Department department) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO |=TABLE=| (name, departmentHeadPid) VALUE ('|=NAME=|', |=DEPARTMENT_HEAD_PID=|);"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=NAME=|", department.getName())
                            .replace("|=DEPARTMENT_HEAD_PID=|", String.valueOf(department.getDepartmentHead().getPid()));
                    statement.executeUpdate(createQuery);

                    String findQuery = "SELECT * FROM |=TABLE=| WHERE name = '|=NAME=|';"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=NAME=|", department.getName());
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new DepartmentDtoDataBaseRepository(resultSet);
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
    public DepartmentDtoDataBaseRepository readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String findQuery = "SELECT * FROM |=TABLE=| WHERE pid = '|=PID=|';"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=PID=|", String.valueOf(pid));
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new DepartmentDtoDataBaseRepository(resultSet);
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
}
