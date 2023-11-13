package kr.ac.kmu.dbp.dto.department;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class DepartmentDtoRepository {
    private int pid;
    private String name;
    private int departmentHeadPid;

    public DepartmentDtoRepository(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("pid");
        this.name = resultSet.getString("name");
        this.departmentHeadPid = resultSet.getInt("departmentHeadPid");
    }
}
