package kr.ac.kmu.dbp.dto.work;

import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

@Getter
public class WorkEnrollDtoCreate {
    private int categorySmallPid;
    private Date workDate;
    private Time startWork;
    private Time endWork;
}
