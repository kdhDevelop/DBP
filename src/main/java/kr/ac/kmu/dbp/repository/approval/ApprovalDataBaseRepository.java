package kr.ac.kmu.dbp.repository.approval;

import kr.ac.kmu.dbp.dto.approval.ApprovalDtoRead;
import kr.ac.kmu.dbp.entity.approval.Approval;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void updateFistApproval(Approval approval) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE approval SET firstApproval = |=APPROVAL=|, firstApprovalDateTime = '|=APPROVAL_DATE_TIME=|', firstApprovalNote = '|=APPROVAL_NOTE=|' WHERE pid = |=PID=| AND firstApprovalEmployeePid = |=EMPLOYEE_PID=| AND firstApproval IS NULL;"
                            .replace("|=APPROVAL_DATE_TIME=|", approval.getFirstApprovalDateTime().toString())
                            .replace("|=APPROVAL_NOTE=|", approval.getFirstApprovalNote())
                            .replace("|=PID=|", String.valueOf(approval.getPid()))
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(approval.getFirstApprovalEmployee().getPid()));
                    if (approval.isFirstApproval())
                        updateQuery = updateQuery.replace("|=APPROVAL=|", "1");
                    else
                        updateQuery = updateQuery.replace("|=APPROVAL=|", "0");

                    System.out.println("UPDATE FIRST APPROVAL QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateSecondApproval(Approval approval) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE approval SET secondApproval = |=APPROVAL=|, secondApprovalDateTime = '|=APPROVAL_DATE_TIME=|', secondApprovalNote = '|=APPROVAL_NOTE=|' WHERE pid = |=PID=| AND secondApprovalEmployeePid = |=EMPLOYEE_PID=| AND firstApproval = 1;"
                            .replace("|=APPROVAL_DATE_TIME=|", approval.getSecondApprovalDateTime().toString())
                            .replace("|=APPROVAL_NOTE=|", approval.getSecondApprovalNote())
                            .replace("|=PID=|", String.valueOf(approval.getPid()))
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(approval.getSecondApprovalEmployee().getPid()));
                    if (approval.isSecondApproval())
                        updateQuery = updateQuery.replace("|=APPROVAL=|", "1");
                    else
                        updateQuery = updateQuery.replace("|=APPROVAL=|", "0");

                    System.out.println("UPDATE SECOND APPROVAL QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Approval> readWaitByEmployee(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Approval> result = new ArrayList<>();
                    String readQuery = "SELECT appr.pid as appr_pid, appr.title as appr_title, appr.content as appr_content, catSmall.pid as catSmall_pid, catSmall.name as catSmall_name, catMedium.pid as catMedium_pid, catMedium.name as catMedium_name, catLarge.pid as catLarge_pid, catLarge.name as catLarge_name, drafterEmp.pid as drafterEmp_pid, drafterEmp.account as drafterEmp_account, drafterEmp.password as drafterEmp_password, drafterEmp.name as drafterEmp_name, drafterEmp.gender as drafterEmp_gender, drafterEmp.birthYear as drafterEmp_birthYear, drafterEmp.wage as drafterEmp_wage, drafterEmp.residentRegistrationNumber as drafterEmp_residentRegistrationNumber, drafterEmp.phoneNumber as drafterEmp_phoneNumber, drafterEmp.zipCode as drafterEmp_zipCode, drafterEmp.address1 as drafterEmp_address1, drafterEmp.address2 as drafterEmp_address2, drafterEmp.role as drafterEmp_role, drafterEmp.rank as drafterEmp_rank, drafterDep.pid as drafterDep_pid, drafterDep.name as drafterDep_name, appr.drafterNote as appr_drafterNote, firstEmp.pid as firstEmp_pid, firstEmp.account as firstEmp_account, firstEmp.password as firstEmp_password, firstEmp.name as firstEmp_name, firstEmp.gender as firstEmp_gender, firstEmp.birthYear as firstEmp_birthYear, firstEmp.wage as firstEmp_wage, firstEmp.residentRegistrationNumber as firstEmp_residentRegistrationNumber, firstEmp.phoneNumber as firstEmp_phoneNumber, firstEmp.zipCode as firstEmp_zipCode, firstEmp.address1 as firstEmp_address1, firstEmp.address2 as firstEmp_address2, firstEmp.role as firstEmp_role, firstEmp.rank as firstEmp_rank, firstDep.pid as firstDep_pid, firstDep.name as firstDep_name, appr.firstApproval as appr_firstApproval, appr.firstApprovalDateTime as appr_firstApprovalDateTime, appr.firstApprovalNote as appr_firstApprovalNote, secondEmp.pid as secondEmp_pid, secondEmp.account as secondEmp_account, secondEmp.password as secondEmp_password, secondEmp.name as secondEmp_name, secondEmp.gender as secondEmp_gender, secondEmp.birthYear as secondEmp_birthYear, secondEmp.wage as secondEmp_wage, secondEmp.residentRegistrationNumber as secondEmp_residentRegistrationNumber, secondEmp.phoneNumber as secondEmp_phoneNumber, secondEmp.zipCode as secondEmp_zipCode, secondEmp.address1 as secondEmp_address1, secondEmp.address2 as secondEmp_address2, secondEmp.role as secondEmp_role, secondEmp.rank as secondEmp_rank, secondDep.pid as secondDep_pid, secondDep.name as secondDep_name, appr.secondApproval as appr_secondApproval, appr.secondApprovalDateTime as appr_secondApprovalDateTime, appr.secondApprovalNote as appr_secondApprovalNote FROM  categorySmall as catSmall, categoryMedium as catMedium, categoryLarge as catLarge, approval as appr,  employee as drafterEmp,  department as drafterDep,  employee as firstEmp,  department as firstDep,  employee secondEmp,  department as secondDep WHERE appr.drafterEmployeePid = drafterEmp.pid AND drafterEmp.departmentPid = drafterDep.pid AND appr.firstApprovalEmployeePid = firstEmp.pid AND firstEmp.departmentPid = firstDep.pid AND appr.secondApprovalEmployeePid = secondEmp.pid AND secondEmp.departmentPid = secondDep.pid AND appr.categorySmallPid = catSmall.pid AND catSmall.categoryMediumPid = catMedium.pid AND catMedium.categoryLargePid = catLarge.pid AND appr.firstApprovalEmployeePid = |=EMPLOYEE_PID=| AND appr.firstApproval IS NULL OR appr.secondApprovalEmployeePid = |=EMPLOYEE_PID=| AND appr.firstApproval = 1 AND appr.secondApproval IS NULL;"
                            .replace("|=EMPLOYEE_PID=|", String.valueOf(employee.getPid()));
                    System.out.println("READ WAIT BY EMPLOYEE QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Approval(resultSet, "appr_", "catSmall_", "catMedium_", "catLarge_", "drafterEmp_", "drafterDep_", "firstEmp_", "firstDep_", "secondEmp_", "secondDep_"));
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
