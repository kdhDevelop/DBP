package kr.ac.kmu.dbp.entity.approval;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class Approval {
    private int pid;

    private String title;
    private String content;
    private CategorySmall categorySmall;

    private Employee drafterEmployee;
    private String drafterNote;

    private Employee firstApprovalEmployee;
    private boolean firstApproval;
    private Timestamp firstApprovalDateTime;
    private String firstApprovalNote;

    private Employee secondApprovalEmployee;
    private boolean secondApproval;
    private Timestamp secondApprovalDateTime;
    private String secondApprovalNote;
}
