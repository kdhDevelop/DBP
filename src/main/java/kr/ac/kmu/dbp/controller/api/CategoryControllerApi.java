package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.dto.work.category.*;
import kr.ac.kmu.dbp.entity.employee.customUserDetails.CustomUserDetails;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import kr.ac.kmu.dbp.service.work.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/category/large/all")
    public List<CategoryLargeDtoRead> readAllLarge() {
        return categoryLargeService.readAll();
    }

    @PostMapping("/category/medium")
    public void createMedium(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategoryMediumDtoCreate categoryMediumDtoCreate) {
        categoryMediumService.create(customUserDetails.getEmployee(), categoryMediumDtoCreate);
    }

    @PutMapping("/category/medium")
    public void updateMedium(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategoryMediumDtoUpdate categoryMediumDtoUpdate) {
        categoryMediumService.update(customUserDetails.getEmployee(), categoryMediumDtoUpdate);
    }

    @DeleteMapping("/category/medium/{pid}")
    public void deleteMedium(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("pid") String pid) {
        categoryMediumService.delete(customUserDetails.getEmployee(), Integer.parseInt(pid));
    }

    @GetMapping("/category/medium/all")
    public List<CategoryMediumDtoRead> readAllMedium() {
        return categoryMediumService.readAll();
    }

    @PostMapping("/category/small")
    public void createSmall(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategorySmallDtoCreate categorySmallDtoCreate) {
        categorySmallService.create(customUserDetails.getEmployee(), categorySmallDtoCreate);
    }

    @PutMapping("/category/small")
    public void updateSmall(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CategorySmallDtoUpdate categorySmallDtoUpdate) {
        categorySmallService.update(customUserDetails.getEmployee(), categorySmallDtoUpdate);
    }

    @DeleteMapping("/category/small/{pid}")
    public void deleteSmall(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("pid") String pid) {
        categorySmallService.delete(customUserDetails.getEmployee(), Integer.parseInt(pid));
    }

    @GetMapping("/category/small/all")
    public List<CategorySmallDtoRead> readAllSmall() {
        return categorySmallService.readAll();
    }

}
