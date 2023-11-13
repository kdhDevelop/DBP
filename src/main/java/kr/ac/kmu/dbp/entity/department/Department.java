package kr.ac.kmu.dbp.entity.department;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Department {
    private int pid;
    private String name;
    private Employee departmentHead;
}
