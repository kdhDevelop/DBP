package kr.ac.kmu.dbp.service.mail;

import kr.ac.kmu.dbp.repository.mail.MailDataBaseRepository;
import kr.ac.kmu.dbp.repository.mail.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;

    @Autowired
    public MailServiceImpl(MailDataBaseRepository mailDataBaseRepository) {
        this.mailRepository = mailDataBaseRepository;
    }
}
