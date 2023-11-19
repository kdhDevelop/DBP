package kr.ac.kmu.dbp.dto.mail;

import kr.ac.kmu.dbp.entity.mail.Mail;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MailDtoReadReceiveInfo {
    private int pid;
    private int senderPid;
    private Timestamp sendDate;
    private String title;

    public MailDtoReadReceiveInfo(Mail mail) {
        this.pid = mail.getPid();
        this.senderPid = mail.getSender().getPid();
        this.sendDate = mail.getSendDate();
        this.title = mail.getTitle();
    }
}
