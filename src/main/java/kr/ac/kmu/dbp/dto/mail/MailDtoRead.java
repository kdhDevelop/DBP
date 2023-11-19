package kr.ac.kmu.dbp.dto.mail;

import kr.ac.kmu.dbp.entity.mail.Mail;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MailDtoRead {

    private int pid;
    private int sender;
    private Timestamp sendDate;

    private String title;
    private String content;

    public MailDtoRead(Mail mail) {
        this.pid = mail.getPid();
        this.sender = mail.getSender().getPid();
        this.sendDate = mail.getSendDate();
        this.title = mail.getTitle();
        this.content = mail.getContent();
    }
}
