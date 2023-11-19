package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.dto.mail.MailDtoRead;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadInfo;
import kr.ac.kmu.dbp.entity.employee.Employee;

import java.util.List;

public interface MailService {
    public void create(Employee sender, MailDtoCreate mailDtoCreate);
    public List<MailDtoReadInfo> readAllInfo(Employee employee);
    public MailDtoRead readByPid(Employee employee, int pid);
    public boolean checkNewMail(Employee employee);
}
