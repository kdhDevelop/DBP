package kr.ac.kmu.dbp.dto.approval;

import lombok.Getter;

@Getter
public class ApprovalDtoCreate {
    private String title;
    private String content;
    private int categorySmallPid;

    private String drafterNote;

    private int firstApprovalEmployeePid;
    private int secondApprovalEmployeePid;
}
