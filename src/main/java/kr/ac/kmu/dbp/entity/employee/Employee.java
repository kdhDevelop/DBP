package kr.ac.kmu.dbp.entity.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRepository;
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

    public Employee(EmployeeDtoRepository employeeDtoRepository) {
        this.pid = employeeDtoRepository.getPid();

        this.account = employeeDtoRepository.getAccount();
        this.password = employeeDtoRepository.getPassword();

        this.name = employeeDtoRepository.getName();
        this.gender = Gender.valueOf(employeeDtoRepository.getGender());

        this.residentRegistrationNumber = employeeDtoRepository.getResidentRegistrationNumber();
        this.phoneNumber = employeeDtoRepository.getPhoneNumber();

        this.zipCode = employeeDtoRepository.getZipCode();
        this.address1 = employeeDtoRepository.getAddress1();
        this.address2 = employeeDtoRepository.getAddress2();

        this.role = Role.valueOf(employeeDtoRepository.getRole());
        this.rank = Rank.valueOf(employeeDtoRepository.getRank());
    }
}
