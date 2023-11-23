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
    private CategorySmall categorySmall;
    private String content;

    private Employee drafter;
    private String note;

    private Employee departmentHead;
    private boolean departmentHeadApproval;
    private Timestamp departmentHeadApprovalDate;
    private String departmentHeadApprovalNote;

    private boolean bossApproval;
    private Timestamp boosApprovalDate;
    private String bossApprovalNote;
}
