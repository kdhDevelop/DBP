package kr.ac.kmu.dbp.repository.mail;

import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.mail.Mail;
import kr.ac.kmu.dbp.repository.DataBaseConnection;
import kr.ac.kmu.dbp.repository.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MailDataBaseRepository extends Table implements MailRepository {

    @Autowired
    public MailDataBaseRepository(DataBaseConnection dataBaseConnection) {
        super(dataBaseConnection, "mail");
    }

    @Override
    protected String getTableCreateQuery() {
        return "CREATE TABLE mail ( pid int NOT NULL AUTO_INCREMENT, senderPid int, sendDate datetime, receiverPid int, receipt bool DEFAULT '0', receiptDate datetime, title varchar(100), content varchar(1000), PRIMARY KEY(pid) );";
    }

    @Override
    public void create(Mail mail) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String createQuery = "INSERT INTO mail (senderPid, sendDate, receiverPid, receipt, title, content) VALUES (|=SENDER_PID=|, '|=SEND_DATE=|', |=RECEIVER_PID=|, |=RECEIPT=|, '|=TITLE=|', '|=CONTENT=|');"
                            .replace("|=SENDER_PID=|", String.valueOf(mail.getSender().getPid()))
                            .replace("|=SEND_DATE=|", mail.getSendDate().toString())
                            .replace("|=RECEIVER_PID=|", String.valueOf(mail.getReceiver().getPid()))
                            .replace("|=RECEIPT=|", "0")
                            .replace("|=TITLE=|", mail.getTitle())
                            .replace("|=CONTENT=|", mail.getContent());
                    System.out.println("CREATE QUERY : " + createQuery);
                    statement.executeUpdate(createQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Mail> readAllReceive(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Mail> result = new ArrayList<>();
                    String readQuery = "SELECT  mail.pid as mail_pid, mail.sendDate as mail_sendDate, mail.receipt as mail_receipt, mail.receiptDate as mail_receiptDate, mail.title as mail_title, mail.content as mail_content, senderEmp.pid as senderEmp_pid, senderEmp.account as senderEmp_account, senderEmp.password as senderEmp_password, senderEmp.name as senderEmp_name, senderEmp.gender as senderEmp_gender, senderEmp.birthYear as senderEmp_birthYear, senderEmp.wage as senderEmp_wage, senderEmp.residentRegistrationNumber as senderEmp_residentRegistrationNumber, senderEmp.phoneNumber as senderEmp_phoneNumber, senderEmp.zipCode as senderEmp_zipCode, senderEmp.address1 as senderEmp_address1, senderEmp.address2 as senderEmp_address2, senderEmp.role as senderEmp_role, senderEmp.rank as senderEmp_rank, senderDep.pid as senderDep_pid, senderDep.name as senderDep_name, receiverEmp.pid as receiverEmp_pid, receiverEmp.account as receiverEmp_account, receiverEmp.password as receiverEmp_password, receiverEmp.name as receiverEmp_name, receiverEmp.gender as receiverEmp_gender, receiverEmp.birthYear as receiverEmp_birthYear, receiverEmp.wage as receiverEmp_wage, receiverEmp.residentRegistrationNumber as receiverEmp_residentRegistrationNumber, receiverEmp.phoneNumber as receiverEmp_phoneNumber, receiverEmp.zipCode as receiverEmp_zipCode, receiverEmp.address1 as receiverEmp_address1, receiverEmp.address2 as receiverEmp_address2, receiverEmp.role as receiverEmp_role, receiverEmp.rank as receiverEmp_rank, receiverDep.pid as receiverDep_pid, receiverDep.name as receiverDep_name FROM  employee as senderEmp, employee as receiverEmp, department as senderDep, department as receiverDep, mail as mail WHERE senderEmp.departmentPid = senderDep.pid AND senderEmp.pid = mail.senderPid AND receiverEmp.departmentPid = receiverDep.pid AND receiverEmp.pid = mail.receiverPid AND mail.receiverPid = |=RECEIVER_PID=|;"
                            .replace("|=RECEIVER_PID=|", String.valueOf(employee.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet= statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Mail(resultSet, "mail_", "senderEmp_", "senderDep_", "receiverEmp_", "receiverDep_"));
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Mail> readAllSend(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Mail> result = new ArrayList<>();
                    String readQuery = "SELECT  mail.pid as mail_pid, mail.sendDate as mail_sendDate, mail.receipt as mail_receipt, mail.receiptDate as mail_receiptDate, mail.title as mail_title, mail.content as mail_content, senderEmp.pid as senderEmp_pid, senderEmp.account as senderEmp_account, senderEmp.password as senderEmp_password, senderEmp.name as senderEmp_name, senderEmp.gender as senderEmp_gender, senderEmp.birthYear as senderEmp_birthYear, senderEmp.wage as senderEmp_wage, senderEmp.residentRegistrationNumber as senderEmp_residentRegistrationNumber, senderEmp.phoneNumber as senderEmp_phoneNumber, senderEmp.zipCode as senderEmp_zipCode, senderEmp.address1 as senderEmp_address1, senderEmp.address2 as senderEmp_address2, senderEmp.role as senderEmp_role, senderEmp.rank as senderEmp_rank, senderDep.pid as senderDep_pid, senderDep.name as senderDep_name, receiverEmp.pid as receiverEmp_pid, receiverEmp.account as receiverEmp_account, receiverEmp.password as receiverEmp_password, receiverEmp.name as receiverEmp_name, receiverEmp.gender as receiverEmp_gender, receiverEmp.birthYear as receiverEmp_birthYear, receiverEmp.wage as receiverEmp_wage, receiverEmp.residentRegistrationNumber as receiverEmp_residentRegistrationNumber, receiverEmp.phoneNumber as receiverEmp_phoneNumber, receiverEmp.zipCode as receiverEmp_zipCode, receiverEmp.address1 as receiverEmp_address1, receiverEmp.address2 as receiverEmp_address2, receiverEmp.role as receiverEmp_role, receiverEmp.rank as receiverEmp_rank, receiverDep.pid as receiverDep_pid, receiverDep.name as receiverDep_name FROM  employee as senderEmp, employee as receiverEmp, department as senderDep, department as receiverDep, mail as mail WHERE senderEmp.departmentPid = senderDep.pid AND senderEmp.pid = mail.senderPid AND receiverEmp.departmentPid = receiverDep.pid AND receiverEmp.pid = mail.receiverPid AND mail.senderPid = |=SENDER_PID=|;"
                            .replace("|=SENDER_PID=|", String.valueOf(employee.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet= statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Mail(resultSet, "mail_", "senderEmp_", "senderDep_", "receiverEmp_", "receiverDep_"));
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Mail readByPid(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT  mail.pid as mail_pid, mail.sendDate as mail_sendDate, mail.receipt as mail_receipt, mail.receiptDate as mail_receiptDate, mail.title as mail_title, mail.content as mail_content, senderEmp.pid as senderEmp_pid, senderEmp.account as senderEmp_account, senderEmp.password as senderEmp_password, senderEmp.name as senderEmp_name, senderEmp.gender as senderEmp_gender, senderEmp.birthYear as senderEmp_birthYear, senderEmp.wage as senderEmp_wage, senderEmp.residentRegistrationNumber as senderEmp_residentRegistrationNumber, senderEmp.phoneNumber as senderEmp_phoneNumber, senderEmp.zipCode as senderEmp_zipCode, senderEmp.address1 as senderEmp_address1, senderEmp.address2 as senderEmp_address2, senderEmp.role as senderEmp_role, senderEmp.rank as senderEmp_rank, senderDep.pid as senderDep_pid, senderDep.name as senderDep_name, receiverEmp.pid as receiverEmp_pid, receiverEmp.account as receiverEmp_account, receiverEmp.password as receiverEmp_password, receiverEmp.name as receiverEmp_name, receiverEmp.gender as receiverEmp_gender, receiverEmp.birthYear as receiverEmp_birthYear, receiverEmp.wage as receiverEmp_wage, receiverEmp.residentRegistrationNumber as receiverEmp_residentRegistrationNumber, receiverEmp.phoneNumber as receiverEmp_phoneNumber, receiverEmp.zipCode as receiverEmp_zipCode, receiverEmp.address1 as receiverEmp_address1, receiverEmp.address2 as receiverEmp_address2, receiverEmp.role as receiverEmp_role, receiverEmp.rank as receiverEmp_rank, receiverDep.pid as receiverDep_pid, receiverDep.name as receiverDep_name FROM  employee as senderEmp, employee as receiverEmp, department as senderDep, department as receiverDep, mail as mail WHERE senderEmp.departmentPid = senderDep.pid AND senderEmp.pid = mail.senderPid AND receiverEmp.departmentPid = receiverDep.pid AND receiverEmp.pid = mail.receiverPid AND mail.pid = |=MAIL_PID=|;"
                            .replace("|=MAIL_PID=|", String.valueOf(pid));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        if (resultSet.next()) {
                            return new Mail(resultSet, "mail_", "senderEmp_", "senderDep_", "receiverEmp_", "receiverDep_");
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateReceipt(int pid) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String updateQuery = "UPDATE mail SET receipt = 1, receiptDate = '|=DATE=|' WHERE pid = |=PID=|;"
                            .replace("|=DATE=|", new Timestamp(System.currentTimeMillis()).toString())
                            .replace("|=PID=|", String.valueOf(pid));
                    System.out.println("UPDATE QUERY : " + updateQuery);
                    statement.executeUpdate(updateQuery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNewMail(Employee employee) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    String readQuery = "SELECT COUNT(*) as count FROM mail WHERE receiverPid = |=PID=| AND receipt = 0;"
                            .replace("|=PID=|", String.valueOf(employee.getPid()));
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        if (resultSet.next()) {
                            return resultSet.getInt("count") > 0;
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Mail> searchReceive(Employee employee, int senderPid, String title, String content) {
        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    List<Mail> result = new ArrayList<>();
                    String readQuery = "";
                    if (senderPid == -1)
                        readQuery = "SELECT  mail.pid as mail_pid, mail.sendDate as mail_sendDate, mail.receipt as mail_receipt, mail.receiptDate as mail_receiptDate, mail.title as mail_title, mail.content as mail_content, senderEmp.pid as senderEmp_pid, senderEmp.account as senderEmp_account, senderEmp.password as senderEmp_password, senderEmp.name as senderEmp_name, senderEmp.gender as senderEmp_gender, senderEmp.birthYear as senderEmp_birthYear, senderEmp.wage as senderEmp_wage, senderEmp.residentRegistrationNumber as senderEmp_residentRegistrationNumber, senderEmp.phoneNumber as senderEmp_phoneNumber, senderEmp.zipCode as senderEmp_zipCode, senderEmp.address1 as senderEmp_address1, senderEmp.address2 as senderEmp_address2, senderEmp.role as senderEmp_role, senderEmp.rank as senderEmp_rank, senderDep.pid as senderDep_pid, senderDep.name as senderDep_name, receiverEmp.pid as receiverEmp_pid, receiverEmp.account as receiverEmp_account, receiverEmp.password as receiverEmp_password, receiverEmp.name as receiverEmp_name, receiverEmp.gender as receiverEmp_gender, receiverEmp.birthYear as receiverEmp_birthYear, receiverEmp.wage as receiverEmp_wage, receiverEmp.residentRegistrationNumber as receiverEmp_residentRegistrationNumber, receiverEmp.phoneNumber as receiverEmp_phoneNumber, receiverEmp.zipCode as receiverEmp_zipCode, receiverEmp.address1 as receiverEmp_address1, receiverEmp.address2 as receiverEmp_address2, receiverEmp.role as receiverEmp_role, receiverEmp.rank as receiverEmp_rank, receiverDep.pid as receiverDep_pid, receiverDep.name as receiverDep_name FROM  employee as senderEmp, employee as receiverEmp, department as senderDep, department as receiverDep, mail as mail WHERE senderEmp.departmentPid = senderDep.pid AND senderEmp.pid = mail.senderPid AND receiverEmp.departmentPid = receiverDep.pid AND receiverEmp.pid = mail.receiverPid AND mail.receiverPid = |=RECEIVER_PID=| AND mail.title LIKE '%|=TITLE=|%' AND mail.content LIKE '%|=CONTENT=|%';"
                                .replace("|=RECEIVER_PID=|", String.valueOf(employee.getPid()))
                                .replace("|=SENDER_PID=|", String.valueOf(senderPid))
                                .replace("|=TITLE=|", title)
                                .replace("|=CONTENT=|", content);
                    else
                        readQuery = "SELECT  mail.pid as mail_pid, mail.sendDate as mail_sendDate, mail.receipt as mail_receipt, mail.receiptDate as mail_receiptDate, mail.title as mail_title, mail.content as mail_content, senderEmp.pid as senderEmp_pid, senderEmp.account as senderEmp_account, senderEmp.password as senderEmp_password, senderEmp.name as senderEmp_name, senderEmp.gender as senderEmp_gender, senderEmp.birthYear as senderEmp_birthYear, senderEmp.wage as senderEmp_wage, senderEmp.residentRegistrationNumber as senderEmp_residentRegistrationNumber, senderEmp.phoneNumber as senderEmp_phoneNumber, senderEmp.zipCode as senderEmp_zipCode, senderEmp.address1 as senderEmp_address1, senderEmp.address2 as senderEmp_address2, senderEmp.role as senderEmp_role, senderEmp.rank as senderEmp_rank, senderDep.pid as senderDep_pid, senderDep.name as senderDep_name, receiverEmp.pid as receiverEmp_pid, receiverEmp.account as receiverEmp_account, receiverEmp.password as receiverEmp_password, receiverEmp.name as receiverEmp_name, receiverEmp.gender as receiverEmp_gender, receiverEmp.birthYear as receiverEmp_birthYear, receiverEmp.wage as receiverEmp_wage, receiverEmp.residentRegistrationNumber as receiverEmp_residentRegistrationNumber, receiverEmp.phoneNumber as receiverEmp_phoneNumber, receiverEmp.zipCode as receiverEmp_zipCode, receiverEmp.address1 as receiverEmp_address1, receiverEmp.address2 as receiverEmp_address2, receiverEmp.role as receiverEmp_role, receiverEmp.rank as receiverEmp_rank, receiverDep.pid as receiverDep_pid, receiverDep.name as receiverDep_name FROM  employee as senderEmp, employee as receiverEmp, department as senderDep, department as receiverDep, mail as mail WHERE senderEmp.departmentPid = senderDep.pid AND senderEmp.pid = mail.senderPid AND receiverEmp.departmentPid = receiverDep.pid AND receiverEmp.pid = mail.receiverPid AND mail.receiverPid = |=RECEIVER_PID=| AND mail.senderPid = |=SENDER_PID=| AND mail.title LIKE '%|=TITLE=|%' AND mail.content LIKE '%|=CONTENT=|%';"
                                .replace("|=RECEIVER_PID=|", String.valueOf(employee.getPid()))
                                .replace("|=SENDER_PID=|", String.valueOf(senderPid))
                                .replace("|=TITLE=|", title)
                                .replace("|=CONTENT=|", content);
                    System.out.println("READ QUERY : " + readQuery);
                    try (ResultSet resultSet = statement.executeQuery(readQuery)) {
                        while (resultSet.next()) {
                            result.add(new Mail(resultSet, "mail_", "senderEmp_", "senderDep_", "receiverEmp_", "receiverDep_"));
                        }
                    }
                    return  result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
