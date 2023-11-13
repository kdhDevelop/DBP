package kr.ac.kmu.dbp.service.department;

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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentDataBaseRepository departmentDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.departmentRepository = departmentDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;
    }

    @Override
    public Department readByPid(int pid) {
        DepartmentDtoRepository departmentDtoRepository = departmentRepository.readByPid(pid);
        Department department = Department.builder()
                .pid(departmentDtoRepository.getPid())
                .name(departmentDtoRepository.getName())
                .build();

        EmployeeDtoRepository employeeDtoRepository = employeeRepository.readByPid(departmentDtoRepository.getPid());
        Employee departmentHead = Employee.builder()
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
                .department(department)
                .rank(Rank.valueOf(employeeDtoRepository.getRank()))
                .build();

        department.setDepartmentHead(departmentHead);

        return department;
    }
}
