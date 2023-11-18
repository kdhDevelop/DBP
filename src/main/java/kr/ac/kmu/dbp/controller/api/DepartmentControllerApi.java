package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.department.DepartmentDtoCreate;
import kr.ac.kmu.dbp.dto.department.DepartmentDtoUpdate;
import kr.ac.kmu.dbp.entity.department.Department;
import kr.ac.kmu.dbp.service.department.DepartmentService;
import kr.ac.kmu.dbp.service.department.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/department")
    public void update(@RequestBody DepartmentDtoUpdate departmentDtoUpdate) {
        departmentService.update(departmentDtoUpdate);
    }

    @GetMapping("/department/all")
    public List<Department> readAll() {
        return departmentService.readAll();
    }
}
