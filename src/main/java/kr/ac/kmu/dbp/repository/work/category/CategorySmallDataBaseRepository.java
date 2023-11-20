package kr.ac.kmu.dbp.repository.work.category;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategorySmallDataBaseRepository extends Table implements CategorySmallRepository {

    @Autowired
    public CategorySmallDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "categorySmall");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE categorySmall ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), categoryMediumPid int, disable bool, PRIMARY KEY(pid) );";
    }
}
