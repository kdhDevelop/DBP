package kr.ac.kmu.dbp.entity.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.dto.department.DepartmentDtoDelete;
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

    public Department(DepartmentDtoCreate departmentDtoCreate) {
        this.name = departmentDtoCreate.getName();
    }

    public Department(String departmentName) {
        this.name = departmentName;
    }

    public Department() {
        this.pid = -1;
        this.name = "무소속";
    }

    public Department(DepartmentDtoDelete departmentDtoDelete) {
        this.pid = departmentDtoDelete.getPid();
        this.name = "삭제 예정";
    }
}
