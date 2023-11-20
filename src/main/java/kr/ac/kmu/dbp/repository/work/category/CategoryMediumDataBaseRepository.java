package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMediumDataBaseRepository extends Table implements CategoryMediumRepository {

    @Autowired
    public CategoryMediumDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categoryMedium");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categoryMedium ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), largeCategoryPid int, disable bool, PRIMARY KEY(pid) );";
    }
}
