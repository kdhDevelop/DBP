package kr.ac.kmu.dbp.repository.approval;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApprovalDataBaseRepository extends Table implements ApprovalRepository {

    @Autowired
    public ApprovalDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "approval");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE approval ( pid int AUTO_INCREMENT, title varchar(1000), categorySmallPid int, content varchar(5000), drafterPid int, note varchar(1000), departmentHeaderPid int, departmentHeadApproval bool, departmentHeadApprovalDate datetime, departmentHeadApprovalNote varchar(1000), bossApproval bool, bossApprovalDate dateTime, bossNote varchar(1000), PRIMARY KEY(pid) );";
    }
}
