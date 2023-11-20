package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
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
public class CategoryLargeDataBaseRepository extends Table implements CategoryLargeRepository {

    @Autowired
    public CategoryLargeDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categoryLarge");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categoryLarge ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), disable bool, PRIMARY KEY(pid) );";
    }

    @Override
    public void create(CategoryLarge categoryLarge) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO categoryLarge (name, disable) VALUES ('|=NAME=|', 0);"
                            .replace("|=NAME=|", categoryLarge.getName());
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(CategoryLarge categoryLarge) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE categoryLarge SET name = '|=NAME=|' WHERE pid = |=PID=| ;"
                            .replace("|=NAME=|", categoryLarge.getName())
                            .replace("|=PID=|", String.valueOf(categoryLarge.getPid()));
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(CategoryLarge categoryLarge) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "UPDATE categoryLarge SET disable = 1 WHERE pid = |=PID=|"
                            .replace("|=PID=|", String.valueOf(categoryLarge.getPid()));
                    statement.executeUpdate(deleteQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategoryLarge> readAll() {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT cat.pid as cat_pid, cat.name as cat_name FROM categoryLarge as cat WHERE disable = 0;";
                    List<CategoryLarge> result = new ArrayList<>();
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new CategoryLarge(resultSet, "cat_"));
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
