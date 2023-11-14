package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.service.department.DepartmentService;
import kr.ac.kmu.dbp.service.department.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DepartmentControllerApi {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentControllerApi(DepartmentServiceImpl departmentServiceImpl) {
        this.departmentService = departmentServiceImpl;
    }

    @PostMapping("/department")
    public void create(@RequestBody DepartmentDtoCreate departmentDtoCreate) {
        departmentService.create(departmentDtoCreate);
    }
}
