package kr.ac.kmu.dbp.dto.approval;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ApprovalDtoCreate {
    private String title;
    private int categorySmallPid;
    private String content;

    private int drafterPid;
    private String note;

    private int departmentHeadPid;
    private boolean departmentHeadApproval;
    private Timestamp departmentHeadApprovalDate;
    private String departmentHeadApprovalNote;

    private boolean bossApproval;
    private Timestamp boosApprovalDate;
    private String bossApprovalNote;
}
