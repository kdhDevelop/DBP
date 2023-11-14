package kr.ac.kmu.dbp.entity.employee;

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
}
