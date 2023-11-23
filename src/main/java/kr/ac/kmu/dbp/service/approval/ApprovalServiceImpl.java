package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoRead;
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

import java.util.ArrayList;
import java.util.List;

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
                .title(approvalDtoCreate.getTitle())
                .content(approvalDtoCreate.getContent())
                .categorySmall(categorySmallRepository.readByPid(approvalDtoCreate.getCategorySmallPid()))
                .drafterEmployee(employee)
                .drafterNote(approvalDtoCreate.getDrafterNote())
                .firstApprovalEmployee(employeeRepository.readByPid(approvalDtoCreate.getFirstApprovalEmployeePid()))
                .secondApprovalEmployee(employeeRepository.readByPid(approvalDtoCreate.getSecondApprovalEmployeePid()))
                .build();
        approvalRepository.create(approval);
    }

    @Override
    public List<ApprovalDtoRead> readWaitByEmployee(Employee employee) {
        List<ApprovalDtoRead> result = new ArrayList<>();
        List<Approval> approvalList = approvalRepository.readWaitByEmployee(employee);
        for (Approval approval : approvalList) {
            result.add(new ApprovalDtoRead(approval));
        }
        return result;
    }
}
