package kr.ac.kmu.dbp.repository.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;

import java.util.List;

public interface MailRepository {
    public void create(Mail mail);
    public List<Mail> readAll(Employee employee);
}
