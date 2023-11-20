package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
