package kr.ac.kmu.dbp.repository.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoDataBaseRepository;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public EmployeeDtoDataBaseRepository create(Employee employee) {
        String createQuery = "INSERT INTO employee (password, name, gender, residentRegistrationNumber, phoneNumber, zipCode, address1, address2, role, departmentPid, rank) VALUES ('|=PASSWORD=|', '|=NAME=|', '|=GENDER=|', '|=RESIDENT_REGISTRATION_NUMBER=|', '|=PHONE_NUMBER=|', |=ZIP_CODE=|, '|=ADDRESS_1=|', '|=ADDRESS_2=|', '|=ROLE=|', |=DEPARTMENT_PID=|, '|=RANK=|');"
                .replace("|=PASSWORD=|", employee.getPassword())
                .replace("|=NAME=|", employee.getName())
                .replace("|=GENDER=|", employee.getGender().toString())
                .replace("|=RESIDENT_REGISTRATION_NUMBER=|", employee.getResidentRegistrationNumber())
                .replace("|=PHONE_NUMBER=|", employee.getPhoneNumber())
                .replace("|=ZIP_CODE=|", String.valueOf(employee.getZipCode()))
                .replace("|=ADDRESS_1=|", employee.getAddress1())
                .replace("|=ADDRESS_2=|", employee.getAddress2())
                .replace("|=ROLE=|", employee.getRole().name())
                .replace("|=DEPARTMENT_PID=|", String.valueOf(employee.getDepartment().getPid()))
                .replace("|=RANK=|", employee.getRank().name());

        String findQuery = "SELECT id FROM employee WHERE residentRegistrationNumber = '|=RESIDENT_REGISTRATION_NUMBER=|'"
                .replace("|=RESIDENT_REGISTRATION_NUMBER=|", employee.getResidentRegistrationNumber());

        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    if (statement.executeUpdate(createQuery) > 0) {
                        String setAccountQuery = "";
                        try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                            if (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String account = "E" + String.format("%05d", id);

                                setAccountQuery = "UPDATE employee SET account = '|=ACCOUNT=|' WHERE id = '|=ID=|'"
                                        .replace("|=ACCOUNT=|", account)
                                        .replace("|=ID=|", String.valueOf(id));
                            }
                        }
                        statement.executeUpdate(setAccountQuery);
                        try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                            if (resultSet.next()) {
                                return new EmployeeDtoDataBaseRepository(resultSet);
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    } else {
                        throw new RuntimeException();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public EmployeeDtoDataBaseRepository readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String findQuery = "SELECT * FROM |=TABLE=| WHERE pid = |=PID=|"
                            .replace("|=TABLE=|", tableName)
                            .replace("|=PID=|", String.valueOf(pid));
                    try (ResultSet resultSet = statement.executeQuery(findQuery)) {
                        if (resultSet.next()) {
                            return new EmployeeDtoDataBaseRepository(resultSet);
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
