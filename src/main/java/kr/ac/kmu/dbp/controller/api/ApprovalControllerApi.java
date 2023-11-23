package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.approval.ApprovalService;
import kr.ac.kmu.dbp.service.approval.ApprovalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApprovalControllerApi {
    private final ApprovalService approvalService;

    @Autowired
    public ApprovalControllerApi(ApprovalServiceImpl approvalServiceImpl) {
        this.approvalService = approvalServiceImpl;
    }

    @PostMapping("/approval")
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ApprovalDtoCreate approvalDtoCreate) {
        approvalService.create(customUserDetails.getEmployee(), approvalDtoCreate);
    }
}
