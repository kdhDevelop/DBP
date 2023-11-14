package kr.ac.kmu.dbp.entity.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
public class Department {
    private int pid;
    private String name;

    public Department(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("depPid");
        this.name = resultSet.getString("depName");
    }
}
