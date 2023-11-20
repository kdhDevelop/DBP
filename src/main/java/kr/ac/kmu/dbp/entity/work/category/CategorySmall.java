package kr.ac.kmu.dbp.entity.work.category;

import lombok.Getter;

@Getter
public class CategorySmall {
    private int pid;
    private String name;
    private CategoryMedium categoryMedium;
}
