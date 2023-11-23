package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface ApprovalService {
    public void create(Employee employee, ApprovalDtoCreate approvalDtoCreate);
}
