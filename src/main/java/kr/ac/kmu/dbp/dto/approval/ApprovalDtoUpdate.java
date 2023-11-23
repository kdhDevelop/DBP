package kr.ac.kmu.dbp.dto.approval;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ApprovalDtoUpdate {
    private int pid;
    private boolean approval;
    private Timestamp approvalTime;
    private String approvalNote;
}
