package kr.ac.kmu.dbp.repository.work;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkEnrollDataBaseRepository extends Table implements WorkEnrollRepository {

    @Autowired
    public WorkEnrollDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "workEnroll");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE workEnroll ( pid int NOT NULL UNIQUE AUTO_INCREMENT, employeePid int, smallCategoryPid int, startWork datetime, endWork datetime, PRIMARY KEY(pid) );";
    }
}
