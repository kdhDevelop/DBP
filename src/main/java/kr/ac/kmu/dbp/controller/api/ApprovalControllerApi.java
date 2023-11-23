package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.approval.ApprovalService;
import kr.ac.kmu.dbp.service.approval.ApprovalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
}
