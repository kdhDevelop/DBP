package kr.ac.kmu.dbp.entity.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoUpdate;
import kr.ac.kmu.dbp.entity.department.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
public class Employee {
    private int pid;

    private String account;
    private String password;

    private String name;
    private Gender gender;

    private String residentRegistrationNumber;
    private String phoneNumber;

    private long zipCode;
    private String address1;
    private String address2;

    private Role role;
    private Rank rank;

    private Department department;

    public Employee(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("empPid");

        this.account = resultSet.getString("empAccount");
        this.password = resultSet.getString("empPassword");

        this.name = resultSet.getString("empName");
        this.gender = Gender.valueOf(resultSet.getString("empGender"));

        this.residentRegistrationNumber = resultSet.getString("empResidentRegistrationNumber");
        this.phoneNumber = resultSet.getString("empPhoneNumber");

        this.zipCode = resultSet.getLong("empZipCode");
        this.address1 = resultSet.getString("empAddress1");
        this.address2 = resultSet.getString("empAddress2");

        this.role = Role.valueOf(resultSet.getString("empRole"));
        this.rank = Rank.valueOf(resultSet.getString("empRank"));

        this.department = new Department(resultSet);
    }

    public Employee(EmployeeDtoCreate employeeDtoCreate) {
        this.password = employeeDtoCreate.getPassword();

        this.name = employeeDtoCreate.getName();
        this.gender = Gender.valueOf(employeeDtoCreate.getGender());

        this.residentRegistrationNumber = employeeDtoCreate.getResidentRegistrationNumber();
        this.phoneNumber = employeeDtoCreate.getPhoneNumber();

        this.zipCode = employeeDtoCreate.getZipCode();
        this.address1 = employeeDtoCreate.getAddress1();
        this.address2 = employeeDtoCreate.getAddress2();

        this.role = Role.valueOf(employeeDtoCreate.getRole());
        this.rank = Rank.valueOf(employeeDtoCreate.getRank());
    }

    public Employee(EmployeeDtoUpdate employeeDtoUpdate) {
        this.name = employeeDtoUpdate.getName();
        this.gender = Gender.valueOf(employeeDtoUpdate.getGender());
        this.phoneNumber = employeeDtoUpdate.getPhoneNumber();

        this.zipCode = employeeDtoUpdate.getZipCode();
        this.address1 = employeeDtoUpdate.getAddress1();
        this.address2 = employeeDtoUpdate.getAddress2();

        this.role = Role.valueOf(employeeDtoUpdate.getRole());
        this.rank = Rank.valueOf(employeeDtoUpdate.getRank());
    }
}
