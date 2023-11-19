package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;

public interface MailService {
    public void create(Employee sender, MailDtoCreate mailDtoCreate);
}
