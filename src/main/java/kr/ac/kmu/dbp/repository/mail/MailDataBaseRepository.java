package kr.ac.kmu.dbp.repository.mail;

import kr.ac.kmu.dbp.entity.mail.Mail;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MailDataBaseRepository extends Table implements MailRepository {

    @Autowired
    public MailDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "mail");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE mail ( pid int NOT NULL AUTO_INCREMENT, senderPid int, sendDate date, receiverPid int, receipt bool DEFAULT(0), receiptDate date, content varchar(1000), PRIMARY KEY(pid) );";
    }

    @Override
    public void create(Mail mail) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO mail (senderPid, sendDate, receiverPid, receipt, title, content) VALUES (|=SENDER_PID=|, |=SEND_DATE=|, |=RECEIVER_PID=|, |=RECEIPT=|, '|=TITLE=|', '|=CONTENT=|');"
                            .replace("|=SENDER_PID=|", String.valueOf(mail.getSender().getPid()))
                            .replace("|=SEND_DATE=|", mail.getSendDate().toString())
                            .replace("|=RECEIVER_PID=|", String.valueOf(mail.getReceiver().getPid()))
                            .replace("|=RECEIPT=|", "0")
                            .replace("|=TITLE=|", mail.getTitle())
                            .replace("|=CONTENT=|", mail.getContent());

                    System.out.println(createQuery);

                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
