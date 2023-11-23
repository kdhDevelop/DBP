package kr.ac.kmu.dbp.service.approval;

import kr.ac.kmu.dbp.repository.approval.ApprovalDataBaseRepository;
import kr.ac.kmu.dbp.repository.approval.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;

    @Autowired
    public ApprovalServiceImpl(ApprovalDataBaseRepository approvalDataBaseRepository) {
        this.approvalRepository = approvalDataBaseRepository;
    }
}
