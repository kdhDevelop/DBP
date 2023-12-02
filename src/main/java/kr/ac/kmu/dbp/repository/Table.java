package kr.ac.kmu.dbp.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Table {

    protected final String tableName;
    protected final DataBaseConnection dataBaseConnection;

    public Table(DataBaseConnection dataBaseConnection, String tableName) {
        this.tableName = tableName;
        this.dataBaseConnection = dataBaseConnection;

        createTableIfNotExist();
    }

    private void createTableIfNotExist() {
        String existCheckQuery = "SHOW TABLES LIKE '|=TABLE=|'"
                .replace("|=TABLE=|", tableName);
        String createQuery = getTableCreateQuery();
        System.out.println("CREATE TABLE QUERY : " + createQuery);
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    boolean exist = false;
                    try (ResultSet resultSet = statement.executeQuery(existCheckQuery)) {
                        exist = resultSet.next();
                    }
                    if (!exist) {
                        statement.executeUpdate(createQuery);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    protected abstract String getTableCreateQuery();
}
