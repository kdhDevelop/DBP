package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import kr.ac.kmu.dbp.repository.mail.MailDataBaseRepository;
import kr.ac.kmu.dbp.repository.mail.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public MailServiceImpl(MailDataBaseRepository mailDataBaseRepository, EmployeeDataBaseRepository employeeRepository) {
        this.mailRepository = mailDataBaseRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void create(Employee sender, MailDtoCreate mailDtoCreate) {
        Employee receiver = employeeRepository.readByPid(mailDtoCreate.getReceiverPid());

        Mail mail = Mail.builder()
                .sender(sender)
                .sendDate(new Timestamp(System.currentTimeMillis()))
                .receiver(receiver)
                .title(mailDtoCreate.getTitle())
                .content(mailDtoCreate.getContent())
                .build();

        mailRepository.create(mail);
    }
}
