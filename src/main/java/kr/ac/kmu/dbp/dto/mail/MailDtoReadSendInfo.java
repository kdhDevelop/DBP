package kr.ac.kmu.dbp.dto.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MailDtoReadSendInfo {
    private int pid;

    private int sender;
    private Timestamp sendDate;

    private int receiver;
    private boolean receipt;
    private Timestamp receiptDate;

    private String title;

    public MailDtoReadSendInfo(Mail mail) {
        this.pid = mail.getPid();
        this.sender = mail.getSender().getPid();
        this.sendDate = mail.getSendDate();
        this.receiver = mail.getReceiver().getPid();
        this.receipt = mail.isReceipt();
        this.receiptDate = mail.getReceiptDate();
        this.title = mail.getTitle();
    }
}
