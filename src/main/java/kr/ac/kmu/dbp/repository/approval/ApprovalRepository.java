package kr.ac.kmu.dbp.repository.approval;

import kr.ac.kmu.dbp.entity.approval.Approval;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface ApprovalRepository {
    public void create(Approval approval);
    public void updateFistApproval(Approval approval);
    public void updateSecondApproval(Approval approval);
    public List<Approval> readByEmployee(Employee employee);
    public List<Approval> readWaitByEmployee(Employee employee);
}
