package kr.ac.kmu.dbp.entity.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class Mail {
    private int pid;

    private Employee sender;
    private Timestamp sendDate;

    private Employee receiver;
    private boolean receipt;
    private Timestamp receiptDate;

    private String title;
    private String content;

    public Mail(ResultSet resultSet, String mailPreFix, String senderPreFix, String senderDepartmentPreFix, String receiverPreFix, String receiverDepartmentPreFix) throws SQLException {
        this.pid = resultSet.getInt(mailPreFix + "pid");
        this.sender = new Employee(resultSet, senderPreFix, senderDepartmentPreFix);
        this.sendDate = resultSet.getTimestamp(mailPreFix + "sendDate");
        this.receiver = new Employee(resultSet, receiverPreFix, receiverDepartmentPreFix);
        this.receipt = resultSet.getBoolean(mailPreFix + "receipt");
        this.title = resultSet.getString(mailPreFix + "title");
        this.content = resultSet.getString(mailPreFix + "content");
    }
}
