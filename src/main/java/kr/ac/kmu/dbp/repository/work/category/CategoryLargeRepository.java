package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;

public interface CategoryLargeRepository {
    public void create(CategoryLarge categoryLarge);
    public void update(CategoryLarge categoryLarge);
    public void delete(CategoryLarge categoryLarge);
}
