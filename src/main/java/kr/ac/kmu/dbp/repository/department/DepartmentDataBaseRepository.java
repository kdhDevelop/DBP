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
import java.util.ArrayList;
import java.util.List;


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
    public void create(Department department) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO |=TABLE=| (name) VALUE ('|=NAME=|');"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=NAME=|", department.getName());
                    statement.executeUpdate(createQuery);
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
                    String findQuery = "SELECT dep.pid as dep_pid, dep.name as dep_name FROM department as dep WHERE pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(pid));
                    System.out.println("READ BY PID QUERY : " + findQuery);
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new Department(resultSet, "dep_");
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
        if (department.getPid() < 3) {
            throw new RuntimeException();
        }

        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "DELETE FROM department WHERE pid = |=DEPARTMENT_PID=|;"
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(department.getPid()));
                    statement.executeUpdate(deleteQuery);
                    String updateQuery = "UPDATE employee SET departmentPid = 2 WHERE departmentPid = |=DEPARTMENT_PID=|;"
                            .replace("|=DEPARTMENT_PID=|", String.valueOf(department.getPid()));
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Department department) {
        if (department.getPid() < 3) {
            throw new RuntimeException();
        }

        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE department SET name = '|=NAME=|' WHERE pid = |=PID=|"
                            .replace("|=NAME=|", department.getName())
                            .replace("|=PID=|", String.valueOf(department.getPid()));
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Department> readAll() {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Department> result = new ArrayList<>();
                    String readQuery = "SELECT dep.pid as dep_pid, dep.name as dep_name FROM department as dep WHERE pid > 2;";
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Department(resultSet, "dep_"));
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
