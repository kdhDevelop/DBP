package kr.ac.kmu.dbp.dto.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryLargeDtoRead {
    private int pid;
    private String name;

    public CategoryLargeDtoRead(CategoryLarge categoryLarge) {
        this.pid = categoryLarge.getPid();
        this.name = categoryLarge.getName();
    }
}
