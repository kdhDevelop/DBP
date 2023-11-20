package kr.ac.kmu.dbp.entity.work.category;

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
public class CategorySmall {
    private int pid;
    private String name;
    private CategoryMedium categoryMedium;

    public CategorySmall(ResultSet resultSet, String categorySmallPrefix, String categoryMediumPrefix, String categoryLargePrefix) throws SQLException {
        this.pid = resultSet.getInt(categorySmallPrefix + "pid");
        this.name = resultSet.getString(categorySmallPrefix + "name");
        this.categoryMedium = new CategoryMedium(resultSet, categoryMediumPrefix, categoryLargePrefix);
    }
}
