package kr.ac.kmu.dbp.repository.work.category;


import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
import kr.ac.kmu.dbp.service.work.category.CategoryMediumService;

import java.util.List;

public interface CategoryMediumRepository {
    public void create(CategoryMedium categoryMedium);
    public void update(CategoryMedium categoryMedium);
    public void delete(CategoryMedium categoryMedium);
    public List<CategoryMedium> readAll();
    public List<CategoryMedium> readByCategoryLarge(CategoryLarge categoryLarge);
    public CategoryMedium readByPid(int pid);
}
