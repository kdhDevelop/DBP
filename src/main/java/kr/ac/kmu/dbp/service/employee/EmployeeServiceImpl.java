package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.dto.employee.EmployeeDtoCreate;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoUpdate;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Gender;
import kr.ac.kmu.dbp.entity.employee.Rank;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.department.DepartmentDataBaseRepository;
import kr.ac.kmu.dbp.repository.department.DepartmentRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeServiceImpl(EmployeeDataBaseRepository employeeDataBaseRepository, DepartmentDataBaseRepository departmentDataBaseRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeDataBaseRepository;
        this.departmentRepository = departmentDataBaseRepository;
        this.passwordEncoder = passwordEncoder;

        init();
    }

    private void init() {
        String[] departmentNameList = new String[] {"사장", "무소속", "인사", "개발", "영업"};

        Employee[] employees = new Employee[13];

        int count = 1;
        String countStr = String.format("%04d", count);

        employees[count-1] = Employee.builder()
                .password(passwordEncoder.encode("password"))
                .name("E" + countStr)
                .gender(Gender.getRandom())
                .residentRegistrationNumber("123456-123" + countStr)
                .phoneNumber("010-1234-" + countStr)
                .zipCode(count)
                .address1("ADDRESS1" + countStr)
                .address2("ADDRESS2" + countStr)
                .role(Role.사장)
                .department(new Department(count, departmentNameList[count-1]))
                .rank(Rank.사장)
                .build();

        count ++;

        for (int T = 3 ; T < 6 ; T ++) {
            for (int TI = 5 ; TI > 1 ; TI --) {
                countStr = String.format("%04d", count);

                Employee.EmployeeBuilder employeeBuilder = Employee.builder()
                        .password(passwordEncoder.encode("password"))
                        .name("E" + countStr)
                        .gender(Gender.getRandom())
                        .residentRegistrationNumber("123456-123" + countStr)
                        .phoneNumber("010-1234-" + countStr)
                        .zipCode(count)
                        .address1("ADDRESS1" + countStr)
                        .address2("ADDRESS2" + countStr)
                        .department(new Department(T, departmentNameList[T-1]))
                        .rank(Rank.values()[TI]);

                if (TI == 0)
                    employeeBuilder = employeeBuilder.role(Role.부서장);
                else
                    employeeBuilder = employeeBuilder.role(Role.직원);

                employees[count-1] = employeeBuilder.build();
                count++;
            }
        }

        for (Employee employee : employees) {
            if (!employeeRepository.checkExistByResidentRegistrationNumber(employee.getResidentRegistrationNumber())) {
                employeeRepository.create(employee);
            }
        }
    }

    @Override
    public Employee readByPid(int pid) {
        return employeeRepository.readByPid(pid);
    }

    @Override
    public Employee readByAccount(String account) {
        return employeeRepository.readByAccount(account);
    }

    @Override
    public void create(EmployeeDtoCreate employeeDtoCreate) {
        Employee employee = new Employee(employeeDtoCreate);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setDepartment(departmentRepository.readByPid(employeeDtoCreate.getDepartmentPid()));

        employeeRepository.create(employee);
    }

    @Override
    public void update(int pid, EmployeeDtoUpdate employeeDtoUpdate) {
        Employee employee = new Employee(employeeDtoUpdate);
        employee.setPid(pid);
        employee.setDepartment(departmentRepository.readByPid(employeeDtoUpdate.getDepartmentPid()));

        employeeRepository.update(employee);
    }
}
