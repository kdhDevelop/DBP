package kr.ac.kmu.dbp.entity.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class Mail {
    private int pid;

    private Employee sender;
    private LocalDateTime sendDate;

    private Employee receiver;
    private boolean receipt;
    private LocalDateTime receiptDate;

    private String title;
    private String content;

    public Mail(ResultSet resultSet, String mailPreFix, String senderPreFix, String senderDepartmentPreFix, String receiverPreFix, String receiverDepartmentPreFix) throws SQLException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.pid = resultSet.getInt(mailPreFix + "pid");
        this.sender = new Employee(resultSet, senderPreFix, senderDepartmentPreFix);
        this.sendDate = LocalDateTime.parse(resultSet.getString(mailPreFix + "sendDate"), format);
        this.receiver = new Employee(resultSet, receiverPreFix, receiverDepartmentPreFix);
        this.receipt = resultSet.getBoolean(mailPreFix + "receipt");
        if (receipt)
            this.receiptDate = LocalDateTime.parse(resultSet.getString((mailPreFix + "receiptDate")), format);
        this.title = resultSet.getString(mailPreFix + "title");
        this.content = resultSet.getString(mailPreFix + "content");
    }
}
