package kr.ac.kmu.dbp.dto.work;

import kr.ac.kmu.dbp.entity.work.WorkEnroll;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
public class WorkEnrollDtoRead {
    private int pid;
    private int employeePid;
    private int categorySmallPid;
    private Date workDate;
    private Time startWork;
    private Time endWork;

    public WorkEnrollDtoRead(WorkEnroll workEnroll) {
        this.pid = workEnroll.getPid();
        this.employeePid = workEnroll.getEmployee().getPid();
        this.categorySmallPid = workEnroll.getCategorySmall().getPid();
        this.workDate = workEnroll.getWorkDate();
        this.startWork = workEnroll.getStartWork();
        this.endWork = workEnroll.getEndWork();
    }
}
