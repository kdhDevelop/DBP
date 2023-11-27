package kr.ac.kmu.dbp.entity.approval;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class Approval {
    private int pid;

    private String title;
    private String content;
    private CategorySmall categorySmall;

    private Employee drafterEmployee;
    private String drafterNote;

    private Employee firstApprovalEmployee;
    private boolean firstApproval;
    private Timestamp firstApprovalDateTime;
    private String firstApprovalNote;

    private Employee secondApprovalEmployee;
    private boolean secondApproval;
    private Timestamp secondApprovalDateTime;
    private String secondApprovalNote;

    public Approval(ResultSet resultSet, String approvalPrefix, String categorySmallPrefix, String categoryMediumPrefix, String categoryLargePrefix, String drafterEmployeePrefix, String drafterDepartmentPrefix, String firstApprovalEmployeePrefix, String firstDepartmentPrefix, String secondApprovalEmployeePrefix, String secondDepartmentPrefix) throws SQLException {
        this.pid = resultSet.getInt(approvalPrefix + "pid");

        this.title = resultSet.getString(approvalPrefix + "title");
        this.content = resultSet.getString(approvalPrefix + "content");
        this.categorySmall = new CategorySmall(resultSet, categorySmallPrefix, categoryMediumPrefix, categoryLargePrefix);

        this.drafterEmployee = new Employee(resultSet, drafterEmployeePrefix, drafterDepartmentPrefix);
        this.drafterNote = resultSet.getString(approvalPrefix + "drafterNote");

        this.firstApprovalEmployee = new Employee(resultSet, firstApprovalEmployeePrefix, firstDepartmentPrefix);
        this.firstApproval = resultSet.getBoolean(approvalPrefix + "firstApproval");
        this.firstApprovalDateTime = resultSet.getTimestamp(approvalPrefix + "firstApprovalDateTime");
        this.firstApprovalNote = resultSet.getString(approvalPrefix + "firstApprovalNote");

        this.secondApprovalEmployee = new Employee(resultSet, secondApprovalEmployeePrefix, secondDepartmentPrefix);
        this.secondApproval = resultSet.getBoolean(approvalPrefix + "secondApproval");
        this.secondApprovalDateTime = resultSet.getTimestamp(approvalPrefix + "secondApprovalDateTime");
        this.secondApprovalNote = resultSet.getString(approvalPrefix + "secondApprovalNote");
    }
}
