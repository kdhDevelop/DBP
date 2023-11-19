package kr.ac.kmu.dbp.repository.mail;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
