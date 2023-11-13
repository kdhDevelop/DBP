package kr.ac.kmu.dbp.entity.employee;

import kr.ac.kmu.dbp.entity.department.Department;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
}
