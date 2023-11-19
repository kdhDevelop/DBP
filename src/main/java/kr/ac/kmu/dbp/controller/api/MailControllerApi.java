package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.mail.MailService;
import kr.ac.kmu.dbp.service.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/mail")
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody MailDtoCreate mailDtoCreate) {
        mailService.create(customUserDetails.getEmployee(), mailDtoCreate);
    }
}
