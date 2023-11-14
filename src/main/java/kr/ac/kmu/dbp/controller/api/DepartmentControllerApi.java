package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.department.DepartmentService;
import kr.ac.kmu.dbp.service.department.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentControllerApi {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentControllerApi(DepartmentServiceImpl departmentServiceImpl) {
        this.departmentService = departmentServiceImpl;
    }
}
