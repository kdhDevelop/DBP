package kr.ac.kmu.dbp.dto.employee;

import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.entity.employee.Gender;
import kr.ac.kmu.dbp.entity.employee.Rank;
import kr.ac.kmu.dbp.entity.employee.Role;
import lombok.Getter;

@Getter
public class EmployeeDtoCreate {
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
}