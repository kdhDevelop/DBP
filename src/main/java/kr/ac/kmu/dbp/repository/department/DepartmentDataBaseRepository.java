package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DepartmentDataBaseRepository extends Table implements DepartmentRepository {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentDataBaseRepository(DataBaseConnection dataBaseConnection, EmployeeDataBaseRepository employeeRepository) {
        super(dataBaseConnection, "department");

        this.employeeRepository = employeeRepository;
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE department ( pid int NOT NULL AUTO_INCREMENT, name varchar(500) UNIQUE, departmentHeadPid int, PRIMARY KEY(pid) );";
    }

}
