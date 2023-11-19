package kr.ac.kmu.dbp.dto.mail;

import lombok.Getter;

@Getter
public class MailDtoCreate {
    private int receiverPid;
    private String title;
    private String content;
}
