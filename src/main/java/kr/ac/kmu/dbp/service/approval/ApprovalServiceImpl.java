package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoRead;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoUpdate;
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
    public void update(Employee employee, ApprovalDtoUpdate approvalDtoUpdate, String state) {
        if (state.equals("first")) {
            Approval approval = Approval.builder()
                    .pid(approvalDtoUpdate.getPid())
                    .firstApprovalEmployee(employee)
                    .firstApproval(approvalDtoUpdate.isApproval())
                    .firstApprovalDateTime(approvalDtoUpdate.getApprovalTime())
                    .firstApprovalNote(approvalDtoUpdate.getApprovalNote())
                    .build();
            approvalRepository.updateFistApproval(approval);
        } else if (state.equals("second")) {
            Approval approval = Approval.builder()
                    .pid(approvalDtoUpdate.getPid())
                    .secondApprovalEmployee(employee)
                    .secondApproval(approvalDtoUpdate.isApproval())
                    .secondApprovalDateTime(approvalDtoUpdate.getApprovalTime())
                    .secondApprovalNote(approvalDtoUpdate.getApprovalNote())
                    .build();
            approvalRepository.updateSecondApproval(approval);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<ApprovalDtoRead> readByEmployee(Employee employee) {
        List<ApprovalDtoRead> result = new ArrayList<>();
        List<Approval> approvalList = approvalRepository.readByEmployee(employee);
        for (Approval approval : approvalList) {
            result.add(new ApprovalDtoRead(approval));
        }
        return result;
    }

    @Override
    public List<ApprovalDtoRead> readWaitByEmployee(Employee employee) {
        List<ApprovalDtoRead> result = new ArrayList<>();
        List<Approval> approvalFirstList = approvalRepository.readByFirstApprovalEmployee(employee);
        for (Approval approval : approvalFirstList) {
            if (approval.getFirstApprovalDateTime() == null) {
                result.add(new ApprovalDtoRead(approval));
            }
        }
        List<Approval> approvalSecondList = approvalRepository.readBySecondApprovalEmployee(employee);
        for (Approval approval : approvalSecondList) {
            if (approval.isFirstApproval() && approval.getSecondApprovalDateTime() == null) {
                result.add(new ApprovalDtoRead(approval));
            }
        }
        return result;
    }
}
