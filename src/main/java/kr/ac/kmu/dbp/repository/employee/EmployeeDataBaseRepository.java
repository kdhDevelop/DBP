package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Gender;
import kr.ac.kmu.dbp.entity.employee.Rank;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeDataBaseRepository extends Table implements EmployeeRepository {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeDataBaseRepository(DataBaseConnection dataBaseConnection, DepartmentDataBaseRepository departmentDataBaseRepository) {
        super(dataBaseConnection, "employee");
        this.departmentRepository = departmentDataBaseRepository;
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE employee ( pid int NOT NULL AUTO_INCREMENT, account varchar(100), password varchar(1000), name varchar(50), gender varchar(50), residentRegistrationNumber char(14) UNIQUE, phoneNumber char(14), zipCode int unsigned, address1 varchar(1000), address2 varchar(1000), role varchar(100), departmentPid int, rank varchar(100), PRIMARY KEY(pid) );";
    }

    private Employee getEmployee(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .pid(resultSet.getInt("pid"))
                .account(resultSet.getString("account"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .residentRegistrationNumber(resultSet.getString("residentRegistrationNumber"))
                .phoneNumber(resultSet.getString("phoneNumber"))
                .zipCode(resultSet.getLong("zipCode"))
                .address1(resultSet.getString("address1"))
                .address2(resultSet.getString("address2"))
                .role(Role.valueOf(resultSet.getString("role")))
                .department(departmentRepository.readByPid(resultSet.getInt("departmentPid")))
                .rank(Rank.valueOf(resultSet.getString("rank")))
                .build();
    }
}
