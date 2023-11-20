package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.work.WorkEnrollService;
import kr.ac.kmu.dbp.service.work.WorkEnrollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkEnrollControllerApi {
    private final WorkEnrollService workEnrollService;

    @Autowired
    public WorkEnrollControllerApi(WorkEnrollServiceImpl workEnrollServiceImpl) {
        this.workEnrollService = workEnrollServiceImpl;
    }
}
