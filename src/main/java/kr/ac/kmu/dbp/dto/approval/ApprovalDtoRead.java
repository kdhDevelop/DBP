package kr.ac.kmu.dbp.dto.approval;

import kr.ac.kmu.dbp.entity.approval.Approval;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ApprovalDtoRead {
    private int pid;

    private String title;
    private String content;
    private int categorySmallPid;

    private int drafterEmployeePid;
    private String drafterNote;

    private int firstApprovalEmployeePid;
    private boolean firstApproval;
    private Timestamp firstApprovalDateTime;
    private String firstApprovalNote;

    private int secondApprovalEmployeePid;
    private boolean secondApproval;
    private Timestamp secondApprovalDateTime;
    private String secondApprovalNote;

    public ApprovalDtoRead(Approval approval) {
        this.pid = approval.getPid();

        this.title = approval.getTitle();
        this.content = approval.getContent();
        this.categorySmallPid = approval.getCategorySmall().getPid();

        this.drafterEmployeePid = approval.getDrafterEmployee().getPid();
        this.drafterNote = approval.getDrafterNote();

        this.firstApprovalEmployeePid = approval.getFirstApprovalEmployee().getPid();
        this.firstApproval = approval.isFirstApproval();
        this.firstApprovalDateTime = approval.getFirstApprovalDateTime();
        this.firstApprovalNote = approval.getFirstApprovalNote();

        this.secondApprovalEmployeePid = approval.getSecondApprovalEmployee().getPid();
        this.secondApproval = approval.isSecondApproval();
        this.secondApprovalDateTime = approval.getSecondApprovalDateTime();
        this.secondApprovalNote = approval.getSecondApprovalNote();
    }
}
