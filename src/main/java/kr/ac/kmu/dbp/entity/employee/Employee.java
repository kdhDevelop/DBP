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
    private int birthYear;
    private int wage;

    private String residentRegistrationNumber;
    private String phoneNumber;

    private long zipCode;
    private String address1;
    private String address2;

    private Role role;
    private Rank rank;

    private Department department;

    public Employee(ResultSet resultSet, String employeePreFix, String departmentPreFix) throws SQLException {
        this.pid = resultSet.getInt(employeePreFix + "pid");

        this.account = resultSet.getString(employeePreFix + "account");
        this.password = resultSet.getString(employeePreFix + "password");
        this.birthYear = resultSet.getInt(employeePreFix + "birthYear");
        this.wage = resultSet.getInt(employeePreFix + "wage");

        this.name = resultSet.getString(employeePreFix + "name");
        this.gender = Gender.valueOf(resultSet.getString(employeePreFix + "gender"));

        this.residentRegistrationNumber = resultSet.getString(employeePreFix + "residentRegistrationNumber");
        this.phoneNumber = resultSet.getString(employeePreFix + "phoneNumber");

        this.zipCode = resultSet.getLong(employeePreFix + "zipCode");
        this.address1 = resultSet.getString(employeePreFix + "address1");
        this.address2 = resultSet.getString(employeePreFix + "address2");

        this.role = Role.valueOf(resultSet.getString(employeePreFix + "role"));
        this.rank = Rank.valueOf(resultSet.getString(employeePreFix + "rank"));

        this.department = new Department(resultSet, departmentPreFix);
    }

    public Employee(EmployeeDtoCreate employeeDtoCreate) {
        this.password = employeeDtoCreate.getPassword();

        this.name = employeeDtoCreate.getName();
        this.gender = Gender.valueOf(employeeDtoCreate.getGender());
        this.birthYear = employeeDtoCreate.getBirthYear();
        this.wage = employeeDtoCreate.getWage();

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
        this.birthYear = employeeDtoUpdate.getBirthYear();
        this.wage = employeeDtoUpdate.getWage();

        this.zipCode = employeeDtoUpdate.getZipCode();
        this.address1 = employeeDtoUpdate.getAddress1();
        this.address2 = employeeDtoUpdate.getAddress2();

        this.role = Role.valueOf(employeeDtoUpdate.getRole());
        this.rank = Rank.valueOf(employeeDtoUpdate.getRank());
    }
}
