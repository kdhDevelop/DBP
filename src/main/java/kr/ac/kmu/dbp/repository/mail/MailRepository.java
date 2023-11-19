package kr.ac.kmu.dbp.repository.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoReadReceiveInfo;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;

import java.util.List;

public interface MailRepository {
    public void create(Mail mail);
    public List<Mail> readAllReceive(Employee employee);
    public List<Mail> readAllSend(Employee employee);
    public Mail readByPid(int pid);
    public void updateReceipt(int pid);
    public boolean checkNewMail(Employee employee);
    public List<Mail> searchReceive(Employee employee, int senderPid, String title, String content);
}
