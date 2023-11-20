package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.mail.MailDtoCreate;
import kr.ac.kmu.dbp.dto.mail.MailDtoRead;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadReceiveInfo;
import kr.ac.kmu.dbp.dto.mail.MailDtoReadSendInfo;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.mail.MailService;
import kr.ac.kmu.dbp.service.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/mail/receive")
    public List<MailDtoReadReceiveInfo> readAllReceiveInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mailService.readAllReceiveInfo(customUserDetails.getEmployee());
    }

    @GetMapping("/mail/send")
    public List<MailDtoReadSendInfo> readAllSendInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mailService.readAllSendInfo(customUserDetails.getEmployee());
    }

    @GetMapping("/mail/{pid}")
    public MailDtoRead readByPid(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("pid") String pid) {
        return mailService.readByPid(customUserDetails.getEmployee(), Integer.parseInt(pid));
    }

    @GetMapping("/mail/check")
    public boolean checkNewMail(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return mailService.checkNewMail(customUserDetails.getEmployee());
    }

    @GetMapping("/mail/receive/search")
    public List<MailDtoReadReceiveInfo> searchReceive(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String senderPid, @RequestParam String title, @RequestParam String content) {
        if (senderPid.isEmpty())
            return mailService.searchReceive(customUserDetails.getEmployee(), -1, title, content);
        else
            return mailService.searchReceive(customUserDetails.getEmployee(), Integer.parseInt(senderPid), title, content);

    }
}
