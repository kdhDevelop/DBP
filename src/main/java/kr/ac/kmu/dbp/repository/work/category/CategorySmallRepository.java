package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategorySmall;

import java.util.List;

public interface CategorySmallRepository {
    public void create(CategorySmall categorySmall);
    public void update(CategorySmall categorySmall);
    public void delete(CategorySmall categorySmall);
    public List<CategorySmall> readAll();
    public CategorySmall readByPid(int pid);
}
