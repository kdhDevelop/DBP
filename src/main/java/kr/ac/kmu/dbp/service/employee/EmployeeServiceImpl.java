package kr.ac.kmu.dbp.service.employee;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoRepository;
import kr.ac.kmu.dbp.dto.employee.EmployeeDtoRepository;
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
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeDataBaseRepository employeeDataBaseRepository, DepartmentDataBaseRepository departmentDataBaseRepository) {
        this.employeeRepository = employeeDataBaseRepository;
        this.departmentRepository = departmentDataBaseRepository;
    }

    @Override
    public Employee readByPid(int pid) {
        EmployeeDtoRepository employeeDtoRepository = employeeRepository.readByPid(pid);
        Employee employee = Employee.builder()
                .pid(employeeDtoRepository.getPid())
                .account(employeeDtoRepository.getAccount())
                .password(employeeDtoRepository.getPassword())
                .name(employeeDtoRepository.getName())
                .gender(Gender.valueOf(employeeDtoRepository.getGender()))
                .residentRegistrationNumber(employeeDtoRepository.getResidentRegistrationNumber())
                .phoneNumber(employeeDtoRepository.getPhoneNumber())
                .zipCode(employeeDtoRepository.getZipCode())
                .address1(employeeDtoRepository.getAddress1())
                .address2(employeeDtoRepository.getAddress2())
                .role(Role.valueOf(employeeDtoRepository.getRole()))
                .rank(Rank.valueOf(employeeDtoRepository.getRank()))
                .build();

        DepartmentDtoRepository departmentDtoRepository = departmentRepository.readByPid(employeeDtoRepository.getDepartmentPid());
        Department department = Department.builder()
                .pid(departmentDtoRepository.getPid())
                .name(departmentDtoRepository.getName())
                .build();
        EmployeeDtoRepository departmentHeadEmployeeDtoRepository = employeeRepository.readByPid(departmentDtoRepository.getPid());
        Employee departmentHead = Employee.builder()
                .pid(departmentHeadEmployeeDtoRepository.getPid())
                .account(departmentHeadEmployeeDtoRepository.getAccount())
                .password(departmentHeadEmployeeDtoRepository.getPassword())
                .name(departmentHeadEmployeeDtoRepository.getName())
                .gender(Gender.valueOf(departmentHeadEmployeeDtoRepository.getGender()))
                .residentRegistrationNumber(departmentHeadEmployeeDtoRepository.getResidentRegistrationNumber())
                .phoneNumber(departmentHeadEmployeeDtoRepository.getPhoneNumber())
                .zipCode(departmentHeadEmployeeDtoRepository.getZipCode())
                .address1(departmentHeadEmployeeDtoRepository.getAddress1())
                .address2(departmentHeadEmployeeDtoRepository.getAddress2())
                .role(Role.valueOf(departmentHeadEmployeeDtoRepository.getRole()))
                .department(department)
                .rank(Rank.valueOf(departmentHeadEmployeeDtoRepository.getRank()))
                .build();
        department.setDepartmentHead(departmentHead);

        employee.setDepartment(department);

        return employee;
    }
}
