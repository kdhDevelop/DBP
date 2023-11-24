package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoRead;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface ApprovalService {
    public void create(Employee employee, ApprovalDtoCreate approvalDtoCreate);
    public void update(Employee employee, ApprovalDtoUpdate approvalDtoUpdate, String state);
    public List<ApprovalDtoRead> readByEmployee(Employee employee);
    public List<ApprovalDtoRead> readWaitByEmployee(Employee employee);
}
