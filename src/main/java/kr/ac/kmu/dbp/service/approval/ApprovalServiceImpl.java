package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.entity.approval.Approval;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.approval.ApprovalDataBaseRepository;
import kr.ac.kmu.dbp.repository.approval.ApprovalRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final CategorySmallRepository categorySmallRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ApprovalServiceImpl(ApprovalDataBaseRepository approvalDataBaseRepository, CategorySmallDataBaseRepository categorySmallDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.approvalRepository = approvalDataBaseRepository;
        this.categorySmallRepository = categorySmallDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;
    }

    @Override
    public void create(Employee employee, ApprovalDtoCreate approvalDtoCreate) {
        Approval approval = Approval.builder()
                .drafter(employee)
                .title(approvalDtoCreate.getTitle())
                .note(approvalDtoCreate.getNote())
                .content(approvalDtoCreate.getContent())
                .categorySmall(categorySmallRepository.readByPid(approvalDtoCreate.getCategorySmallPid()))
                .departmentHead(employeeRepository.readByPid(approvalDtoCreate.getDepartmentHeadPid()))
                .build();
        approvalRepository.create(approval);
    }
}
