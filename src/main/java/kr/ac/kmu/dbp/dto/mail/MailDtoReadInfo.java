package kr.ac.kmu.dbp.dto.mail;

import kr.ac.kmu.dbp.entity.mail.Mail;

import java.sql.Timestamp;

public class MailDtoReadInfo {
    private int pid;
    private int senderPid;
    private Timestamp sendDate;
    private String title;

    public MailDtoReadInfo(Mail mail) {
        this.pid = mail.getPid();
        this.senderPid = mail.getSender().getPid();
        this.sendDate = mail.getSendDate();
        this.title = mail.getTitle();
    }
}
