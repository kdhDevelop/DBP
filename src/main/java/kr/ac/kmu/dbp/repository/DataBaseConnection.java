package kr.ac.kmu.dbp.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class DataBaseConnection {

    private final String DB_IP = "192.168.11.194";
    private final String DB_PORT = "13306";
    private final String DB_ID = "root";
    private final String DB_PW = "qwerty@233456";
    private final String DB_NAME = "WorkerManager";
    private final HikariDataSource hikariDataSource;

    public DataBaseConnection() {

        String url = "jdbc:mariadb://|=IP=|:|=PORT=|/|=NAME=|"
                .replace("|=IP=|", DB_IP)
                .replace("|=PORT=|", DB_PORT)
                .replace("|=NAME=|", DB_NAME);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(DB_ID);
        config.setPassword(DB_PW);
        config.setConnectionTimeout(300);
        config.setMaximumPoolSize(20);

        hikariDataSource = new HikariDataSource(config);
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}