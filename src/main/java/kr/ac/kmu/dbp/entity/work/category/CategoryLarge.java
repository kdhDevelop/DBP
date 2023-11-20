package kr.ac.kmu.dbp.entity.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import lombok.Getter;

@Getter
public class CategoryLarge {
    private int pid;
    private String name;

    public CategoryLarge(CategoryLargeDtoCreate categoryLargeDtoCreate) {
        this.name = categoryLargeDtoCreate.getName();
    }
}
