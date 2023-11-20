package kr.ac.kmu.dbp.entity.work.category;

import lombok.Getter;

@Getter
public class CategoryMedium {
    private int pid;
    private String name;
    private CategoryLarge categoryLarge;
}
