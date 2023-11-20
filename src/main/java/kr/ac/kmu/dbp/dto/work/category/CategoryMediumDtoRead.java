package kr.ac.kmu.dbp.dto.work.category;

import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryMediumDtoRead {
    private int pid;
    private String name;
    private int categoryLargePid;

    public CategoryMediumDtoRead(CategoryMedium categoryMedium) {
        this.pid = categoryMedium.getPid();
        this.name = categoryMedium.getName();
        this.categoryLargePid = categoryMedium.getCategoryLarge().getPid();
    }
}
