package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoCreate;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoRead;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.work.WorkEnrollService;
import kr.ac.kmu.dbp.service.work.WorkEnrollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkEnrollControllerApi {
    private final WorkEnrollService workEnrollService;

    @Autowired
    public WorkEnrollControllerApi(WorkEnrollServiceImpl workEnrollServiceImpl) {
        this.workEnrollService = workEnrollServiceImpl;
    }

    @PostMapping("/work")
    public void create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody  WorkEnrollDtoCreate workEnrollDtoCreate) {
        workEnrollService.create(customUserDetails.getEmployee(), workEnrollDtoCreate);
    }

    @PutMapping("/work")
    public void update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody WorkEnrollDtoUpdate workEnrollDtoUpdate) {
        workEnrollService.update(customUserDetails.getEmployee(), workEnrollDtoUpdate);
    }

    @DeleteMapping("/work/{pid}")
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("pid") String pid) {
        workEnrollService.delete(customUserDetails.getEmployee(), Integer.parseInt(pid));
    }

    @GetMapping("/work/all/{date}")
    public List<WorkEnrollDtoRead> readAll(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String date) throws ParseException {
        return workEnrollService.readAll(customUserDetails.getEmployee(), Date.valueOf(date));
    }

    @GetMapping(value = "/work/search", params = {"date", "categorySmallPid"})
    public List<WorkEnrollDtoRead> searchByDateAndCategorySmallPid(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String date, @RequestParam String categorySmallPid) {
        return workEnrollService.searchByDateAndCategorySmallPid(customUserDetails.getEmployee(), Date.valueOf(date), Integer.parseInt(categorySmallPid));
    }

    @GetMapping(value = "/work/search", params = "employeePid")
    public List<WorkEnrollDtoRead> searchByEmployeePid(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String employeePid) {
        return workEnrollService.searchByEmployeePid(customUserDetails.getEmployee(), Integer.parseInt(employeePid));
    }
}
