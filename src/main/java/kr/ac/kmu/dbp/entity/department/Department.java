package kr.ac.kmu.dbp.entity.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoRepository;
import kr.ac.kmu.dbp.entity.employee.Employee;
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

    public Department(DepartmentDtoRepository departmentDtoRepository) {
        this.pid = departmentDtoRepository.getPid();
        this.name = departmentDtoRepository.getName();
    }

    public Department(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("depPid");
        this.name = resultSet.getString("depName");
    }
}
