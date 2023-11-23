package kr.ac.kmu.dbp.entity.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoUpdate;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryLarge {
    private int pid;
    private String name;

    public CategoryLarge(ResultSet resultSet, String prefix) throws SQLException {
        this.pid = resultSet.getInt(prefix + "pid");
        this.name = resultSet.getString(prefix + "name");
    }

    public CategoryLarge(int pid) {
        this.pid = pid;
    }

    public CategoryLarge(CategoryLargeDtoCreate categoryLargeDtoCreate) {
        this.name = categoryLargeDtoCreate.getName();
    }

    public CategoryLarge(CategoryLargeDtoUpdate categoryLargeDtoUpdate) {
        this.pid = categoryLargeDtoUpdate.getPid();
        this.name = categoryLargeDtoUpdate.getName();
    }
}
