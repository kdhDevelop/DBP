package kr.ac.kmu.dbp.entity.department;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoRepository;
import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Department {
    private int pid;
    private String name;
    private Employee departmentHead;

    public Department(DepartmentDtoRepository departmentDtoRepository) {
        this.pid = departmentDtoRepository.getPid();
        this.name = departmentDtoRepository.getName();
    }
}
