package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.dto.mail.MailDtoRead;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadInfo;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import kr.ac.kmu.dbp.repository.mail.MailDataBaseRepository;
import kr.ac.kmu.dbp.repository.mail.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MailDtoReadInfo> readAllInfo(Employee employee) {
        List<MailDtoReadInfo> result = new ArrayList<>();

        for (Mail mail : mailRepository.readAll(employee)) {
            result.add(new MailDtoReadInfo(mail));
        }

        return result;
    }

    @Override
    public MailDtoRead readByPid(Employee employee, int pid) {
        Mail mail = mailRepository.readByPid(pid);
        if (mail.getReceiver().getPid() == employee.getPid()) {
            if (!mail.isReceipt()) {
                mailRepository.updateReceipt(pid);
            }
            return new MailDtoRead(mail);
        } else {
            throw new RuntimeException();
        }
    }
}
