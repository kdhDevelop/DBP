package kr.ac.kmu.dbp.entity.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
public class Mail {
    private int pid;

    private Employee sender;
    private Date sendDate;

    private Employee receiver;
    private boolean receipt;
    private Date receiptDate;

    private String title;
    private String content;
}
