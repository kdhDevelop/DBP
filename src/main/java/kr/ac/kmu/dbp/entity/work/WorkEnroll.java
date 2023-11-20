package kr.ac.kmu.dbp.entity.work;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class WorkEnroll {
    private int pid;
    private Employee employee;
    private CategorySmall categorySmall;
    private Timestamp startWork;
    private Timestamp endWork;
}
