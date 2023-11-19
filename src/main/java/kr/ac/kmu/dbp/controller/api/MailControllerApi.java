package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.mail.MailService;
import kr.ac.kmu.dbp.service.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MailControllerApi {
    private final MailService mailService;

    @Autowired
    public MailControllerApi(MailServiceImpl mailServiceImpl) {
        this.mailService = mailServiceImpl;
    }

}
