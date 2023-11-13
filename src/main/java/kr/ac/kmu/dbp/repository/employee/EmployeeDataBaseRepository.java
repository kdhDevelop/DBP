package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataBaseRepository extends Table implements EmployeeRepository {
    @Autowired
    public EmployeeDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "employee");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE employee ( pid int NOT NULL AUTO_INCREMENT, account varchar(100), password varchar(1000), name varchar(50), age smallint, gender varchar(50), residentRegistrationNumber char(14) UNIQUE, phoneNumber char(14), zipCode int unsigned, address1 varchar(1000), address2 varchar(1000), role varchar(100), departmentPid int, rank varchar(100), createDate date, PRIMARY KEY(pid) );";
    }
}
