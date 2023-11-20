package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.service.work.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
