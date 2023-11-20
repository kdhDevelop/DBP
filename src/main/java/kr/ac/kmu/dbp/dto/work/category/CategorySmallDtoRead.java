package kr.ac.kmu.dbp.dto.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySmallDtoRead {
    private int pid;
    private String name;
    private int categoryMediumPid;

    public CategorySmallDtoRead(CategorySmall categorySmall) {
        this.pid = categorySmall.getPid();
        this.name = categorySmall.getName();
        this.categoryMediumPid = categorySmall.getCategoryMedium().getPid();
    }
}
