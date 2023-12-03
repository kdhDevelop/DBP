package kr.ac.kmu.dbp.dto.approval;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class ApprovalDtoUpdate {
    private int pid;
    private boolean approval;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvalTime;
    private String approvalNote;
}
