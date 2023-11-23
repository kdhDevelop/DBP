package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoCreate;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoRead;
import kr.ac.kmu.dbp.dto.approval.ApprovalDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.approval.ApprovalService;
import kr.ac.kmu.dbp.service.approval.ApprovalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/approval/wait")
    public List<ApprovalDtoRead> readWait(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return approvalService.readWaitByEmployee(customUserDetails.getEmployee());
    }

    @PutMapping("/approval/{state}")
    public void update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ApprovalDtoUpdate approvalDtoUpdate, @PathVariable String state) {
        approvalService.update(customUserDetails.getEmployee(), approvalDtoUpdate, state);
    }
}
