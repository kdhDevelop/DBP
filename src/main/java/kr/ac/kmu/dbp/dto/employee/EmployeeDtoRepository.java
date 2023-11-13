package kr.ac.kmu.dbp.dto.employee;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class EmployeeDtoRepository {
    private int pid;

    private String account;
    private String password;

    private String name;
    private String gender;
    private String residentRegistrationNumber;
    private String phoneNumber;

    private long zipCode;
    private String address1;
    private String address2;

    private String role;
    private String rank;

    private int departmentPid;

    public EmployeeDtoRepository(ResultSet resultSet) throws SQLException {
        this.pid = resultSet.getInt("pid");

        this.account = resultSet.getString("account");
        this.password = resultSet.getString("password");

        this.name = resultSet.getString("name");
        this.gender = resultSet.getString("gender");
        this.residentRegistrationNumber = resultSet.getString("residentRegistrationNumber");
        this.phoneNumber = resultSet.getString("phoneNumber");

        this.zipCode = resultSet.getLong("zipCode");
        this.address1 = resultSet.getString("address1");
        this.address2 = resultSet.getString("address2");

        this.role = resultSet.getString("role");
        this.rank = resultSet.getString("rank");

        this.departmentPid = resultSet.getInt("departmentPid");
    }
}
