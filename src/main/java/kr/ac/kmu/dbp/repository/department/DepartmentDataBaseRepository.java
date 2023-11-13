package kr.ac.kmu.dbp.repository.department;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDataBaseRepository extends Table implements DepartmentRepository {

    @Autowired
    public DepartmentDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "department");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE department ( pid int NOT NULL AUTO_INCREMENT, name varchar(500), departmentHeadPid int, PRIMARY KEY(pid) );";
    }
}
