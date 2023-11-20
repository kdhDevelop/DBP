package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryLargeDataBaseRepository extends Table implements CategoryLargeRepository {

    @Autowired
    public CategoryLargeDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categoryLarge");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categoryLarge ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), disable bool, PRIMARY KEY(pid) );";
    }
}
