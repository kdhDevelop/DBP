package kr.ac.kmu.dbp.controller.api;

import kr.ac.kmu.dbp.repository.DataBaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataBaseInitializingControllerApi {
    private DataBaseConnection dataBaseConnection;

    @Autowired
    public DataBaseInitializingControllerApi(DataBaseConnection dataBaseConnection) {
        this.dataBaseConnection = dataBaseConnection;
    }

    @GetMapping("/db")
    public void database() {

        List<String> queries = new ArrayList<>();
        String tableQueries = "" +
                "DROP TABLE employee;\n" +
                "CREATE TABLE employee ( pid int NOT NULL AUTO_INCREMENT, account varchar(100), password varchar(1000), name varchar(50), gender varchar(50), birthYear int, wage int, residentRegistrationNumber char(14) UNIQUE, phoneNumber char(14), zipCode int unsigned, address1 varchar(1000), address2 varchar(1000), role varchar(100), departmentPid int, rank varchar(100), PRIMARY KEY(pid) );\n" +
                "DROP TABLE department;\n" +
                "CREATE TABLE department ( pid int NOT NULL AUTO_INCREMENT, name varchar(500) UNIQUE, PRIMARY KEY(pid) );\n" +
                "DROP TABLE approval;\n" +
                "CREATE TABLE approval ( pid int AUTO_INCREMENT, title varchar(1000), content varchar(5000), categorySmallPid int, drafterEmployeePid int, drafterNote varchar(1000), firstApprovalEmployeePid int, firstApproval bool, firstApprovalDateTime datetime, firstApprovalNote varchar(1000), secondApprovalEmployeePid int, secondApproval bool, secondApprovalDateTime datetime, secondApprovalNote varchar(1000), PRIMARY KEY(pid) );\n" +
                "DROP TABLE attendance;\n" +
                "CREATE TABLE attendance ( employeePid int, attendanceDate date, dayOfWeek varchar(10), startTime time, endTime time, wage int, PRIMARY KEY(employeePid, attendanceDate) );\n" +
                "DROP TABLE categorySmall;\n" +
                "CREATE TABLE categorySmall ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), categoryMediumPid int, disable bool, PRIMARY KEY(pid) );\n" +
                "DROP TABLE categoryMedium;\n" +
                "CREATE TABLE categoryMedium ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), categoryLargePid int, disable bool, PRIMARY KEY(pid) );\n" +
                "DROP TABLE categoryLarge;\n" +
                "CREATE TABLE categoryLarge ( pid int NOT NULL UNIQUE AUTO_INCREMENT, name varchar(100), disable bool, PRIMARY KEY(pid) );\n" +
                "DROP TABLE mail;\n" +
                "CREATE TABLE mail ( pid int NOT NULL AUTO_INCREMENT, senderPid int, sendDate datetime, receiverPid int, receipt bool DEFAULT '0', receiptDate datetime, title varchar(100), content varchar(1000), PRIMARY KEY(pid) );\n" +
                "DROP TABLE workEnroll;\n" +
                "CREATE TABLE workEnroll ( pid int NOT NULL UNIQUE AUTO_INCREMENT, employeePid int, workDate date, categorySmallPid int, startWork time, endWork time, PRIMARY KEY(pid) );";

        queries.add(tableQueries);

        String departmentQueries = "" +
                "INSERT INTO department (name) VALUE ('사장');\n" +
                "INSERT INTO department (name) VALUE ('무소속');\n" +
                "INSERT INTO department (name) VALUE ('인사');\n" +
                "INSERT INTO department (name) VALUE ('개발');\n" +
                "INSERT INTO department (name) VALUE ('영업');";

        queries.add(departmentQueries);

        String categoriesQueries = "" +
                "INSERT INTO categoryLarge (name, disable) VALUES ('인사', 0);\n" +
                "INSERT INTO categoryLarge (name, disable) VALUES ('개발', 0);\n" +
                "INSERT INTO categoryLarge (name, disable) VALUES ('영업', 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('정기 업무', 1, 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('단기 업무', 1, 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('정기 업무', 2, 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('단기 업무', 2, 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('정기 업무', 3, 0);\n" +
                "INSERT INTO categoryMedium (name, categoryLargePid, disable) VALUES ('단기 업무', 3, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('근퇴 확인', 1, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('급여 중간 계산', 1, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('급여 계산', 1, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('사무실 청소', 2, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('일간 회의 진행', 3, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('개발', 3, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('점검', 3, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('사무실 청소', 4, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('고객사 방문', 5, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('영업 이익 중간 정산', 5, 0);\n" +
                "INSERT INTO categorySmall (name, categoryMediumPid, disable) VALUES ('사무실 청소', 6, 0);";

        queries.add(categoriesQueries);

        String employeeQueries = "" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (1,'평가용 기본 데이터 결재 신청 1','평가용 기본 데이터 결재 신청 1 내용',1,3,'평가용 기본 데이터 결재 신청 1 메모',2,1,'2023-12-01 05:28:20','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:44','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (2,'평가용 기본 데이터 결재 신청 2','평가용 기본 데이터 결재 신청 2 내용',2,3,'평가용 기본 데이터 결재 신청 2 메모',2,0,'2023-12-01 05:28:45','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (3,'평가용 기본 데이터 결재 신청 3','평가용 기본 데이터 결재 신청 3 내용',3,3,'평가용 기본 데이터 결재 신청 3 메모',2,1,'2023-12-01 05:28:26','평가용 기본 데이터 결재 신청 1차 승인',1,0,'2023-12-01 05:29:59','평가용 기본 데이터 결재 신청 2차 반려');\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (4,'평가용 기본 데이터 결재 신청 4','평가용 기본 데이터 결재 신청 4 내용',4,3,'평가용 기본 데이터 결재 신청 4 메모',2,0,'2023-12-01 05:28:48','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (5,'평가용 기본 데이터 결재 신청 5','평가용 기본 데이터 결재 신청 5 내용',5,3,'평가용 기본 데이터 결재 신청 5 메모',2,1,'2023-12-01 05:28:29','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:48','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (6,'평가용 기본 데이터 결재 신청 6','평가용 기본 데이터 결재 신청 6 내용',6,3,'평가용 기본 데이터 결재 신청 6 메모',2,0,'2023-12-01 05:28:50','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (7,'평가용 기본 데이터 결재 신청 7','평가용 기본 데이터 결재 신청 7 내용',7,3,'평가용 기본 데이터 결재 신청 7 메모',2,1,'2023-12-01 05:28:32','',1,0,'2023-12-01 05:30:01','평가용 기본 데이터 결재 신청 2차 반려');\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (8,'평가용 기본 데이터 결재 신청 8','평가용 기본 데이터 결재 신청 8 내용',8,3,'평가용 기본 데이터 결재 신청 8 메모',2,0,'2023-12-01 05:28:53','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (9,'평가용 기본 데이터 결재 신청 9','평가용 기본 데이터 결재 신청 9 내용',5,3,'평가용 기본 데이터 결재 신청 9 메모',2,1,'2023-12-01 05:28:35','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:52','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (10,'평가용 기본 데이터 결재 신청 10','평가용 기본 데이터 결재 신청 10 내용',6,3,'평가용 기본 데이터 결재 신청 10 메모',2,0,'2023-12-01 05:28:56','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO WorkerManager.approval (pid,title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES (11,'평가용 기본 데이터 결재 신청 11','평가용 기본 데이터 결재 신청 11 내용',7,3,'평가용 기본 데이터 결재 신청 11 메모',2,1,'2023-12-01 05:28:38','평가용 기본 데이터 결재 신청 1차 승인',1,0,'2023-12-01 05:30:04','평가용 기본 데이터 결재 신청 2차 반려');";

        queries.add(employeeQueries);

        String attendanceQueries = "" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-16', 'MONDAY', '09:00:00', '18:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-16', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-16', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-16', 'MONDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-16', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-16', 'MONDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-16', 'MONDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-16', 'MONDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-16', 'MONDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-16', 'MONDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-16', 'MONDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-16', 'MONDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-16', 'MONDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-17', 'TUESDAY', '09:00:00', '18:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-17', 'TUESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-17', 'TUESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-17', 'TUESDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-17', 'TUESDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-17', 'TUESDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-17', 'TUESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-17', 'TUESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-17', 'TUESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-17', 'TUESDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-17', 'TUESDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-17', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-17', 'TUESDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-18', 'WEDNESDAY', '09:00:00', '23:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-18', 'WEDNESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-18', 'WEDNESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-18', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-18', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-18', 'WEDNESDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-18', 'WEDNESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-18', 'WEDNESDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-18', 'WEDNESDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-18', 'WEDNESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-18', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-18', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-18', 'WEDNESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-19', 'THURSDAY', '09:00:00', '23:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-19', 'THURSDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-19', 'THURSDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-19', 'THURSDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-19', 'THURSDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-19', 'THURSDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-19', 'THURSDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-19', 'THURSDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-19', 'THURSDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-19', 'THURSDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-19', 'THURSDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-19', 'THURSDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-19', 'THURSDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-20', 'FRIDAY', '09:00:00', '17:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-20', 'FRIDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-20', 'FRIDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-20', 'FRIDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-20', 'FRIDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-20', 'FRIDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-20', 'FRIDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-20', 'FRIDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-20', 'FRIDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-20', 'FRIDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-20', 'FRIDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-20', 'FRIDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-20', 'FRIDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-21', 'SATURDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-21', 'SATURDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-21', 'SATURDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-21', 'SATURDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-21', 'SATURDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-21', 'SATURDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-21', 'SATURDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-21', 'SATURDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-21', 'SATURDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-21', 'SATURDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-21', 'SATURDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-21', 'SATURDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-21', 'SATURDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-23', 'MONDAY', '09:00:00', '17:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-23', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-23', 'MONDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-23', 'MONDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-23', 'MONDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-23', 'MONDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-23', 'MONDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-23', 'MONDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-23', 'MONDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-23', 'MONDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-23', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-23', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-23', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-24', 'TUESDAY', '09:00:00', '20:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-24', 'TUESDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-24', 'TUESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-24', 'TUESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-24', 'TUESDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-24', 'TUESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-24', 'TUESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-24', 'TUESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-24', 'TUESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-24', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-24', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-24', 'TUESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-24', 'TUESDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-25', 'WEDNESDAY', '09:00:00', '22:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-25', 'WEDNESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-25', 'WEDNESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-25', 'WEDNESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-25', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-25', 'WEDNESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-25', 'WEDNESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-25', 'WEDNESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-25', 'WEDNESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-25', 'WEDNESDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-25', 'WEDNESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-25', 'WEDNESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-25', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-26', 'THURSDAY', '09:00:00', '23:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-26', 'THURSDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-26', 'THURSDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-26', 'THURSDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-26', 'THURSDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-26', 'THURSDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-26', 'THURSDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-26', 'THURSDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-26', 'THURSDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-26', 'THURSDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-26', 'THURSDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-26', 'THURSDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-26', 'THURSDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-27', 'FRIDAY', '09:00:00', '21:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-27', 'FRIDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-27', 'FRIDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-27', 'FRIDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-27', 'FRIDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-27', 'FRIDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-27', 'FRIDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-27', 'FRIDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-27', 'FRIDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-27', 'FRIDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-27', 'FRIDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-27', 'FRIDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-27', 'FRIDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-28', 'SATURDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-28', 'SATURDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-28', 'SATURDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-28', 'SATURDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-28', 'SATURDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-28', 'SATURDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-28', 'SATURDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-28', 'SATURDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-28', 'SATURDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-28', 'SATURDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-28', 'SATURDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-28', 'SATURDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-28', 'SATURDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-30', 'MONDAY', '09:00:00', '23:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-30', 'MONDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-30', 'MONDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-30', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-30', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-30', 'MONDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-30', 'MONDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-30', 'MONDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-30', 'MONDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-30', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-30', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-30', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-30', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-10-31', 'TUESDAY', '09:00:00', '17:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-10-31', 'TUESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-10-31', 'TUESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-10-31', 'TUESDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-10-31', 'TUESDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-10-31', 'TUESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-10-31', 'TUESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-10-31', 'TUESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-10-31', 'TUESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-10-31', 'TUESDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-10-31', 'TUESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-10-31', 'TUESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-10-31', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-01', 'WEDNESDAY', '09:00:00', '20:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-01', 'WEDNESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-01', 'WEDNESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-01', 'WEDNESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-01', 'WEDNESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-01', 'WEDNESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-01', 'WEDNESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-01', 'WEDNESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-01', 'WEDNESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-01', 'WEDNESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-01', 'WEDNESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-01', 'WEDNESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-01', 'WEDNESDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-02', 'THURSDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-02', 'THURSDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-02', 'THURSDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-02', 'THURSDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-02', 'THURSDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-02', 'THURSDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-02', 'THURSDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-03', 'FRIDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-03', 'FRIDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-03', 'FRIDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-03', 'FRIDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-03', 'FRIDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-03', 'FRIDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-03', 'FRIDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-03', 'FRIDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-03', 'FRIDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-03', 'FRIDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-03', 'FRIDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-03', 'FRIDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-03', 'FRIDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-04', 'SATURDAY', '09:00:00', '23:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-04', 'SATURDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-04', 'SATURDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-04', 'SATURDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-04', 'SATURDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-04', 'SATURDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-04', 'SATURDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-04', 'SATURDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-04', 'SATURDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-04', 'SATURDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-04', 'SATURDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-04', 'SATURDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-04', 'SATURDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-06', 'MONDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-06', 'MONDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-06', 'MONDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-06', 'MONDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-06', 'MONDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-06', 'MONDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-06', 'MONDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-06', 'MONDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-06', 'MONDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-06', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-06', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-06', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-06', 'MONDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-07', 'TUESDAY', '09:00:00', '22:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-07', 'TUESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-07', 'TUESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-07', 'TUESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-07', 'TUESDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-07', 'TUESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-07', 'TUESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-07', 'TUESDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-07', 'TUESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-07', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-07', 'TUESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-07', 'TUESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-07', 'TUESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-08', 'WEDNESDAY', '09:00:00', '22:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-08', 'WEDNESDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-08', 'WEDNESDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-08', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-08', 'WEDNESDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-08', 'WEDNESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-08', 'WEDNESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-08', 'WEDNESDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-08', 'WEDNESDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-08', 'WEDNESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-08', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-08', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-08', 'WEDNESDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-09', 'THURSDAY', '09:00:00', '22:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-09', 'THURSDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-09', 'THURSDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-09', 'THURSDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-09', 'THURSDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-09', 'THURSDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-09', 'THURSDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-09', 'THURSDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-09', 'THURSDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-09', 'THURSDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-09', 'THURSDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-09', 'THURSDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-09', 'THURSDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-10', 'FRIDAY', '09:00:00', '22:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-10', 'FRIDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-10', 'FRIDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-10', 'FRIDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-10', 'FRIDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-10', 'FRIDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-10', 'FRIDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-10', 'FRIDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-10', 'FRIDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-10', 'FRIDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-10', 'FRIDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-10', 'FRIDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-10', 'FRIDAY', '09:00:00', '17:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-11', 'SATURDAY', '09:00:00', '17:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-11', 'SATURDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-11', 'SATURDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-11', 'SATURDAY', '09:00:00', '22:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-11', 'SATURDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-11', 'SATURDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-11', 'SATURDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-11', 'SATURDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-11', 'SATURDAY', '09:00:00', '17:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-11', 'SATURDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-11', 'SATURDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-11', 'SATURDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-11', 'SATURDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-13', 'MONDAY', '09:00:00', '20:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-13', 'MONDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-13', 'MONDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-13', 'MONDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-13', 'MONDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-13', 'MONDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-13', 'MONDAY', '09:00:00', '22:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-13', 'MONDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-13', 'MONDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-13', 'MONDAY', '09:00:00', '18:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-13', 'MONDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-13', 'MONDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-13', 'MONDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-14', 'TUESDAY', '09:00:00', '17:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-14', 'TUESDAY', '09:00:00', '19:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-14', 'TUESDAY', '09:00:00', '18:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-14', 'TUESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-14', 'TUESDAY', '09:00:00', '21:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-14', 'TUESDAY', '09:00:00', '19:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-14', 'TUESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-14', 'TUESDAY', '09:00:00', '20:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-14', 'TUESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-14', 'TUESDAY', '09:00:00', '20:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-14', 'TUESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-14', 'TUESDAY', '09:00:00', '19:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-14', 'TUESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (1, '2023-11-15', 'WEDNESDAY', '09:00:00', '19:00:00', 100000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (2, '2023-11-15', 'WEDNESDAY', '09:00:00', '20:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (3, '2023-11-15', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (4, '2023-11-15', 'WEDNESDAY', '09:00:00', '17:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (5, '2023-11-15', 'WEDNESDAY', '09:00:00', '23:00:00', 13000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (6, '2023-11-15', 'WEDNESDAY', '09:00:00', '21:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (7, '2023-11-15', 'WEDNESDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (8, '2023-11-15', 'WEDNESDAY', '09:00:00', '18:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (9, '2023-11-15', 'WEDNESDAY', '09:00:00', '23:00:00', 14000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (10, '2023-11-15', 'WEDNESDAY', '09:00:00', '23:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (11, '2023-11-15', 'WEDNESDAY', '09:00:00', '22:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (12, '2023-11-15', 'WEDNESDAY', '09:00:00', '21:00:00', 15000);\n" +
                "INSERT INTO attendance (employeePid, attendanceDate, dayOfWeek, startTime, endTime, wage) VALUES (13, '2023-11-15', 'WEDNESDAY', '09:00:00', '21:00:00', 15000);";

        queries.add(attendanceQueries);

        String workQueries = "" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 2, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 3, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 4, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 5, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 6, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 7, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 8, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 9, '09:00:00', '12:00:00');\n" +
                "INSERT INTO workEnroll (employeePid, workDate, categorySmallPid, startWork, endWork) VALUES (1, '2023-10-16', 10, '09:00:00', '12:00:00');";

        queries.add(workQueries);

        String approvalQueries = "" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 1','평가용 기본 데이터 결재 신청 1 내용',1,3,'평가용 기본 데이터 결재 신청 1 메모',2,1,'2023-12-01 05:28:20','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:44','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 2','평가용 기본 데이터 결재 신청 2 내용',2,3,'평가용 기본 데이터 결재 신청 2 메모',2,0,'2023-12-01 05:28:45','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 3','평가용 기본 데이터 결재 신청 3 내용',3,3,'평가용 기본 데이터 결재 신청 3 메모',2,1,'2023-12-01 05:28:26','평가용 기본 데이터 결재 신청 1차 승인',1,0,'2023-12-01 05:29:59','평가용 기본 데이터 결재 신청 2차 반려');\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 4','평가용 기본 데이터 결재 신청 4 내용',4,3,'평가용 기본 데이터 결재 신청 4 메모',2,0,'2023-12-01 05:28:48','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 5','평가용 기본 데이터 결재 신청 5 내용',5,3,'평가용 기본 데이터 결재 신청 5 메모',2,1,'2023-12-01 05:28:29','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:48','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 6','평가용 기본 데이터 결재 신청 6 내용',6,3,'평가용 기본 데이터 결재 신청 6 메모',2,0,'2023-12-01 05:28:50','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 7','평가용 기본 데이터 결재 신청 7 내용',7,3,'평가용 기본 데이터 결재 신청 7 메모',2,1,'2023-12-01 05:28:32','평가용 기본 데이터 결재 신청 1차 승인',1,0,'2023-12-01 05:30:01','평가용 기본 데이터 결재 신청 2차 반려');\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 8','평가용 기본 데이터 결재 신청 8 내용',8,3,'평가용 기본 데이터 결재 신청 8 메모',2,0,'2023-12-01 05:28:53','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 9','평가용 기본 데이터 결재 신청 9 내용',5,3,'평가용 기본 데이터 결재 신청 9 메모',2,1,'2023-12-01 05:28:35','평가용 기본 데이터 결재 신청 1차 승인',1,1,'2023-12-01 05:29:52','평가용 기본 데이터 결재 신청 2차 승인');\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 10','평가용 기본 데이터 결재 신청 10 내용',6,3,'평가용 기본 데이터 결재 신청 10 메모',2,0,'2023-12-01 05:28:56','평가용 기본 데이터 결재 신청 1차 반려',1,NULL,NULL,NULL);\n" +
                "INSERT INTO approval (title,content,categorySmallPid,drafterEmployeePid,drafterNote,firstApprovalEmployeePid,firstApproval,firstApprovalDateTime,firstApprovalNote,secondApprovalEmployeePid,secondApproval,secondApprovalDateTime,secondApprovalNote) VALUES ('평가용 기본 데이터 결재 신청 11','평가용 기본 데이터 결재 신청 11 내용',7,3,'평가용 기본 데이터 결재 신청 11 메모',2,1,'2023-12-01 05:28:38','평가용 기본 데이터 결재 신청 1차 승인',1,0,'2023-12-01 05:30:04','평가용 기본 데이터 결재 신청 2차 반려');\n";

        queries.add(approvalQueries);

        try {
            try (Connection connection = dataBaseConnection.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    for (String queryString : queries) {
                        String[] queryList = queryString.split("\\n");
                        for(String query : queryList) {
                            System.out.println(query);
                            statement.executeUpdate(query);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
