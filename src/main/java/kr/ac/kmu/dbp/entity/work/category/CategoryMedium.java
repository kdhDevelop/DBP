package kr.ac.kmu.dbp.entity.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryMedium {
    private int pid;
    private String name;
    private CategoryLarge categoryLarge;

    public CategoryMedium(ResultSet resultSet, String categoryMediumPrefix, String categoryLargePrefix) throws SQLException {
        this.pid = resultSet.getInt(categoryMediumPrefix + "pid");
        this.name = resultSet.getString(categoryMediumPrefix + "name");
        this.categoryLarge = new CategoryLarge(resultSet, categoryLargePrefix);
    }
}
