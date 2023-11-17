package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
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
        return "CREATE TABLE department ( pid int NOT NULL AUTO_INCREMENT, name varchar(500) UNIQUE, PRIMARY KEY(pid) );";
    }

    @Override
    public Department create(Department department) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO |=TABLE=| (name) VALUE ('|=NAME=|');"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=NAME=|", department.getName());
                    statement.executeUpdate(createQuery);

                    String findQuery = "SELECT dep.pid as depPid, dep.name as depName FROM department as dep WHERE name = '|=NAME=|';"
                            .replace("|=NAME=|", department.getName());
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new Department(resultSet);
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
    public Department readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String findQuery = "SELECT dep.pid as depPid, dep.name as depName FROM department as dep WHERE pid = '|=PID=|';"
                            .replace("|=PID=|", String.valueOf(pid));
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new Department(resultSet);
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
    public boolean checkExistByName(String name) {
       try {
           try (Connection connection = dataBaseConnection.getConnection()) {
               try (Statement statement = connection.createStatement()) {
                   String checkExistQuery = "SELECT * FROM department WHERE name = '|=NAME=|';"
                           .replace("|=NAME=|", name);
                   try (ResultSet resultSet = statement.executeQuery(checkExistQuery)) {
                       return resultSet.next();
                   }
               }
           }
       } catch (SQLException e) {
           throw new RuntimeException();
       }
    }

    @Override
    public void delete(Department department) {

        if (department.getPid() < 2) {
            return;
        }

        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "DELETE FROM department WHERE pid = |=DEPARTMENT_PID=|"
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(department.getPid()));
                    statement.executeUpdate(deleteQuery);
                    String updateQuery = "UPDATE employee departmentPid = 1 WHERE departmentPid = |=DEPARTMENT_PID=|"
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(department.getPid()));
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
