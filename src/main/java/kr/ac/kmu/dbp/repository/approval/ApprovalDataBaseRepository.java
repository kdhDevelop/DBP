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
        return "CREATE TABLE approval ( pid int AUTO_INCREMENT, title varchar(1000), content varchar(5000), categorySmallPid int, drafterEmployeePid int, drafterNote varchar(1000), firstApprovalEmployeePid int, firstApproval bool, firstApprovalDateTime datetime, firstApprovalNote varchar(1000), secondApprovalEmployeePid int, secondApproval bool, secondApprovalDateTime datetime, secondApprovalNote varchar(1000), PRIMARY KEY(pid) );";
    }

    @Override
    public void create(Approval approval) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO approval (title, content, categorySmallPid, drafterEmployeePid, drafterNote, firstApprovalEmployeePid, secondApprovalEmployeePid) VALUES ('|=TITLE=|', '|=CONTENT=|', |=CATEGORY_SMALL_PID=|, |=DRAFTER_EMPLOYEE_PID=|, '|=DRAFTER_NOTE=|', |=FIRST_APPROVAL_EMPLOYEE_PID=|, |=SECOND_APPROVAL_EMPLOYEE_PID=|);"
                            .replace("|=TITLE=|", approval.getTitle())
                            .replace("|=CONTENT=|", approval.getContent())
                            .replace("|=CATEGORY_SMALL_PID=|", String.valueOf(approval.getCategorySmall().getPid()))
                            .replace("|=DRAFTER_EMPLOYEE_PID=|", String.valueOf(approval.getDrafterEmployee().getPid()))
                            .replace("|=DRAFTER_NOTE=|", approval.getDrafterNote())
                            .replace("|=FIRST_APPROVAL_EMPLOYEE_PID=|", String.valueOf(approval.getFirstApprovalEmployee().getPid()))
                            .replace("|=SECOND_APPROVAL_EMPLOYEE_PID=|", String.valueOf(approval.getSecondApprovalEmployee().getPid()));
                    System.out.println("CREATE APPROVAL QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
