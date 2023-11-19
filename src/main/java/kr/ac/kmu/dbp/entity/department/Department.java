package kr.ac.kmu.dbp.entity.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.dto.department.DepartmentDtoUpdate;
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

    public Department(ResultSet resultSet, String prefix) throws SQLException {
        this.pid = resultSet.getInt(prefix + "pid");
        this.name = resultSet.getString(prefix + "name");
    }

    public Department(DepartmentDtoCreate departmentDtoCreate) {
        this.name = departmentDtoCreate.getName();
    }

    public Department(String departmentName) {
        this.name = departmentName;
    }

    public Department(int pid) {
        this.pid = pid;
    }

    public Department(DepartmentDtoUpdate departmentDtoUpdate) {
        this.pid = departmentDtoUpdate.getPid();
        this.name = departmentDtoUpdate.getName();
    }

}
