package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
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
public class CategorySmallDataBaseRepository extends Table implements CategorySmallRepository {

    @Autowired
    public CategorySmallDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categorySmall");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categorySmall ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), categoryMediumPid int, disable bool, PRIMARY KEY(pid) );";
    }

    @Override
    public void create(CategorySmall categorySmall) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('|=NAME=|', |=CATEGORY_MEDIUM_PID=|, 0);"
                            .replace("|=NAME=|", categorySmall.getName())
                            .replace("|=CATEGORY_MEDIUM_PID=|", String.valueOf(categorySmall.getCategoryMedium().getPid()));
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(CategorySmall categorySmall) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE categorySmall SET name = '|=NAME=|', categoryMediumPid = |=CATEGORY_MEDIUM_PID=| WHERE pid = |=PID=|;"
                            .replace("|=NAME=|", categorySmall.getName())
                            .replace("|=CATEGORY_MEDIUM_PID=|", String.valueOf(categorySmall.getCategoryMedium().getPid()))
                            .replace("|=PID=|", String.valueOf(categorySmall.getPid()));
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(CategorySmall categorySmall) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String deleteQuery = "UPDATE categorySmall SET disable = 1 WHERE pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(categorySmall.getPid()));
                    statement.executeUpdate(deleteQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategorySmall> readAll() {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<CategorySmall> result = new ArrayList<>();
                    String readQuery = "SELECT catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid  = catLarge.pid AND catSmall.disable = 0;";
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new CategorySmall(resultSet, "catSmall_", "catMedium_", "catLarge_"));
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
    public List<CategorySmall> readByCategoryMedium(CategoryMedium categoryMedium) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<CategorySmall> result = new ArrayList<>();
                    String readQuery = "SELECT catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid  = catLarge.pid AND catSmall.disable = 0 AND catMedium.pid = |=CATEGORY_MEDIUM_PID=|;"
                            .replace("|=CATEGORY_MEDIUM_PID=|", String.valueOf(categoryMedium.getPid()));
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new CategorySmall(resultSet, "catSmall_", "catMedium_", "catLarge_"));
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
    public CategorySmall readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name FROM categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge WHERE catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid  = catLarge.pid AND catSmall.pid = |=PID=|;"
                            .replace("|=PID=|", String.valueOf(pid));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        if (resultSet.next()) {
                            return new CategorySmall(resultSet, "catSmall_", "catMedium_", "catLarge_");
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
    public boolean checkExistByCategoryMediumPidAndName(int categoryMediumPid, String name) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT * FROM categorySmall WHERE disable = 0 AND categoryMediumPid = |=CATEGORY_MEDIUM_PID=| AND name = '|=NAME=|';"
                            .replace("|=CATEGORY_MEDIUM_PID=|", String.valueOf(categoryMediumPid))
                            .replace("|=NAME=|", name);
                    System.out.println("CHECK EXIST CATEGORY SMALL BY CATEGORY MEDIUM PID AND NAME QUERY : " + readQuery);
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
