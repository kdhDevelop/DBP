package kr.ac.kmu.dbp.dto.department;

import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class DepartmentDtoDataBaseRepository {
    private int pid;
    private String name;
    private int departmentHeadPid;

    public DepartmentDtoDataBaseRepository(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("pid");
        this.name = resultSet.getString("name");
        this.departmentHeadPid = resultSet.getInt("departmentHeadPid");
    }
}
