package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
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
public class CategoryMediumDataBaseRepository extends Table implements CategoryMediumRepository {

    @Autowired
    public CategoryMediumDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categoryMedium");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categoryMedium ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), categoryLargePid int, disable bool, PRIMARY KEY(pid) );";
    }

    @Override
    public void create(CategoryMedium categoryMedium) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('|=NAME=|', |=CATEGORY_LARGE_PID=|, 0);"
                            .replace("|=NAME=|", categoryMedium.getName())
                            .replace("|=CATEGORY_LARGE_PID=|", String.valueOf(categoryMedium.getCategoryLarge().getPid()));
                    System.out.println("CATEGORY MEDIUM CREATE QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(CategoryMedium categoryMedium) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE categoryMedium SET name = '|=NAME=|', categoryLargePid = |=CATEGORY_LARGE_PID=| WHERE pid = '|=PID=|';"
                            .replace("|=NAME=|", categoryMedium.getName())
                            .replace("|=CATEGORY_LARGE_PID=|", String.valueOf(categoryMedium.getCategoryLarge().getPid()))
                            .replace("|=PID=|", String.valueOf(categoryMedium.getPid()));
                    System.out.println("UPDATE QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(CategoryMedium categoryMedium) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "UPDATE categoryMedium SET disable = 1 WHERE pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(categoryMedium.getPid()));
                    System.out.println("DELETE QUERY : " + deleteQuery);
                    statement.executeUpdate(deleteQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public List<CategoryMedium> readAll() {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<CategoryMedium> result = new ArrayList<>();
                    String readQuery = "SELECT catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categoryLarge as catLarge, categoryMedium as catMedium WHERE catMedium.categoryLargePid = catLarge.pid AND catMedium.disable = 0;";
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new CategoryMedium(resultSet, "catMedium_", "catLarge_"));
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
    public List<CategoryMedium> readByCategoryLarge(CategoryLarge categoryLarge) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<CategoryMedium> result = new ArrayList<>();
                    String readQuery = "SELECT catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categoryLarge as catLarge, categoryMedium as catMedium WHERE catMedium.categoryLargePid = catLarge.pid AND catMedium.disable = 0 AND catLarge.pid = |=CATEGORY_LARGE_PID=|;"
                            .replace("|=CATEGORY_LARGE_PID=|", String.valueOf(categoryLarge.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new CategoryMedium(resultSet, "catMedium_", "catLarge_"));
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
    public CategoryMedium readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categoryLarge as catLarge, categoryMedium as catMedium WHERE catMedium.categoryLargePid = catLarge.pid AND catMedium.pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(pid));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        if (resultSet.next()) {
                            return new CategoryMedium(resultSet, "catMedium_", "catLarge_");
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
    public boolean checkExistByCategoryLargePidAndName(int categoryLargePid, String name) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT * FROM categoryMedium WHERE disable = 0 AND categoryLargePid = |=CATEGORY_LARGE_PID=| AND name = '|=NAME=|';"
                            .replace("|=CATEGORY_LARGE_PID=|", String.valueOf(categoryLargePid))
                            .replace("|=NAME=|", name);
                    System.out.println("CHECK EXIST CATEGORY MEDIUM BY CATEGORY LARGE PID AND NAME QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        return resultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
