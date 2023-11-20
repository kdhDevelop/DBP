package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.service.work.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryControllerApi {
    private final CategoryLargeService categoryLargeService;
    private final CategoryMediumService categoryMediumService;
    private final CategorySmallService categorySmallService;

    @Autowired
    public CategoryControllerApi(CategoryLargeServiceImpl categoryLargeServiceImpl, CategoryMediumServiceImpl categoryMediumServiceImpl, CategorySmallServiceImpl categorySmallServiceImpl) {
        this.categoryLargeService = categoryLargeServiceImpl;
        this.categoryMediumService = categoryMediumServiceImpl;
        this.categorySmallService = categorySmallServiceImpl;
    }

    @PostMapping("/category/large")
    public void createLarge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategoryLargeDtoCreate categoryLargeDtoCreate) {
        categoryLargeService.create(customUserDetails.getEmployee(), categoryLargeDtoCreate);
    }

    @PutMapping("/category/large")
    public void updateLarge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategoryLargeDtoUpdate categoryLargeDtoUpdate) {
        categoryLargeService.update(customUserDetails.getEmployee(), categoryLargeDtoUpdate);
    }

    @DeleteMapping("/category/large/{pid}")
    public void deleteLarge(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("pid") String pid) {
        categoryLargeService.delete(customUserDetails.getEmployee(), Integer.parseInt(pid));
    }
}
