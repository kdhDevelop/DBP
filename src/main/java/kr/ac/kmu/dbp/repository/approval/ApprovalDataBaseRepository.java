package kr.ac.kmu.dbp.repository.approval;

import kr.ac.kmu.dbp.entity.approval.Approval;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public void create(Approval approval) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO approval (title, categorySmallPid, content, drafterPid, note, departmentHeaderPid) VALUES ('|=TITLE=|', |=CATEGORY_SMALL_PID=|, '|=CONTENT=|', |=DRAFTER_PID=|, '|=NOTE=|', |=DEPARTMENT_HEADER_PID=|);"
                            .replace("|=TITLE=|", approval.getTitle())
                            .replace("|=CATEGORY_SMALL_PID=|", String.valueOf(approval.getCategorySmall().getPid()))
                            .replace("|=CONTENT=|", approval.getContent())
                            .replace("|=DRAFTER_PID=|", String.valueOf(approval.getDrafter().getPid()))
                            .replace("|=NOTE=|", approval.getNote())
                            .replace("|=DEPARTMENT_HEADER_PID=|", String.valueOf(approval.getDepartmentHead().getPid()));
                    System.out.println("CREATE APPROVAL QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
