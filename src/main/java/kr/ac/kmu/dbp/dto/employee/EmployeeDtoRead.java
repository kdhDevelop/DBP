package kr.ac.kmu.dbp.dto.employee;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDtoRead {

    private int pid;

    private String account;
    private String name;
    private String gender;
    private int birthYear;
    private int wage;

    private String phoneNumber;

    private long zipCode;
    private String address1;
    private String address2;

    private String role;
    private String rank;

    private int departmentPid;

    public EmployeeDtoRead(Employee employee) {
        this.pid = employee.getPid();

        this.account = employee.getAccount();
        this.name = employee.getName();
        this.gender = employee.getGender().name();
        this.birthYear = employee.getBirthYear();
        this.wage = employee.getWage();

        this.phoneNumber = employee.getPhoneNumber();

        this.zipCode = employee.getZipCode();
        this.address1 = employee.getAddress1();
        this.address2 = employee.getAddress2();

        this.role = employee.getRole().name();
        this.rank = employee.getRank().name();

        this.departmentPid = employee.getDepartment().getPid();
    }
}
