package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.dto.mail.MailDtoRead;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadReceiveInfo;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadSendInfo;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface MailService {
    public void create(Employee sender, MailDtoCreate mailDtoCreate);
    public List<MailDtoReadReceiveInfo> readAllReceiveInfo(Employee employee);
    public List<MailDtoReadSendInfo> readAllSendInfo(Employee employee);
    public MailDtoRead readByPid(Employee employee, int pid);
    public boolean checkNewMail(Employee employee);
    public List<MailDtoReadReceiveInfo> searchReceive(Employee employee, int senderPid, String title, String content);
}
