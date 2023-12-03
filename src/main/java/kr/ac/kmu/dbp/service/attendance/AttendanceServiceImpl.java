package kr.ac.kmu.dbp.service.attendance;

import kr.ac.kmu.dbp.dto.attendance.*;
import kr.ac.kmu.dbp.entity.attendance.Attendance;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.repository.attendance.AttendanceDataBaseRepository;
import kr.ac.kmu.dbp.repository.attendance.AttendanceRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceDataBaseRepository attendanceDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.attendanceRepository = attendanceDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;

        //init();
    }

    private void init() {

        Employee[] employees = new Employee[14];
        for (int T = 0 ; T < 13 ; T ++) {
            employees[T] = Employee.builder().pid(T).build();
        }

        LocalDate startDate = LocalDate.of(2023, 10, 15);
        LocalTime startTime = LocalTime.of(9, 0, 0);
        for (int T = 0 ; T < 32 ; T ++) {
            LocalDate currDate = startDate.plusDays(T);
            if (currDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            for (int TI = 0 ; TI < 13 ; TI ++) {
                Employee employee = employees[TI];
                int randomHour = (int) (Math.random() * 7);
                LocalTime currTime = startTime.plusHours(8 + randomHour);
                Attendance attendance = Attendance.builder()
                        .employee(employee)
                        .attendanceDate(Date.valueOf(currDate))
                        .dayOfWeek(currDate.getDayOfWeek())
                        .startTime(Time.valueOf(startTime))
                        .endTime(Time.valueOf(currTime))
                        .wage(employee.getWage())
                        .build();
                if (!attendanceRepository.checkExistByEmployeeAndAttendanceDate(attendance)) {
                    attendanceRepository.create(attendance);
                }
            }
        }




        for (int T = 1 ; T < 14 ; T ++) {
            Employee employee = employeeRepository.readByPid(T);
            for (int TI = 0 ; TI < 31 ; TI ++) {

            }
        }
    }

    @Override
    public void create(Employee employee, AttendanceDtoCreate attendanceDtoCreate) {
        Attendance attendance = Attendance.builder()
                .employee(employee)
                .attendanceDate(attendanceDtoCreate.getAttendanceDate())
                .dayOfWeek(DayOfWeek.valueOf(attendanceDtoCreate.getDayOfWeek()))
                .startTime(attendanceDtoCreate.getStartTime())
                .endTime(attendanceDtoCreate.getEndTime())
                .wage(employee.getWage())
                .build();
        attendanceRepository.create(attendance);
    }

    @Override
    public List<AttendanceDtoRead> readByEmployee(Employee reader, Employee target) {
        if (reader.getRole() == Role.부서장 || reader.getRole() == Role.사장 || reader.getPid() == target.getPid()) {
            List<AttendanceDtoRead> result = new ArrayList<>();
            List<Attendance> attendanceList = attendanceRepository.readByEmployee(target);
            for (Attendance attendance : attendanceList) {
                result.add(new AttendanceDtoRead(attendance));
            }
            return result;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<AttendanceDtoRead> readByEmployeeAndYearAndMonth(Employee employee, int year, int month) {
        List<AttendanceDtoRead> result = new ArrayList<>();

        LocalDate startDate = LocalDate.of(year, month, 15);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Attendance> attendanceList = attendanceRepository.readByEmployeeAndBetweenAttendanceDate(employee, Date.valueOf(startDate), Date.valueOf(endDate));

        for (Attendance attendance : attendanceList) {
            result.add(new AttendanceDtoRead(attendance));
        }

        return result;
    }

    @Override
    public AttendanceWorkSalaryDtoRead calculateWorkSalaryByEmployeeAndYearAndMonth(Employee employee, int year, int month, AttendanceWageMultiple attendanceWageMultiple) {
        LocalDate startDate = LocalDate.of(year, month, 15);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Attendance> attendanceList = attendanceRepository.readByEmployeeAndBetweenAttendanceDate(employee, Date.valueOf(startDate), Date.valueOf(endDate));

        LocalTime endWorkTime = LocalTime.of(17, 0, 0);
        LocalTime endOverWorkTime = LocalTime.of(20, 0, 0);

        int workTime = 0;
        int workWage = 0;

        int overWorkTime = 0;
        double overWorkMultiple = attendanceWageMultiple.getOverWorkMultiple();
        int overWorkWage = 0;

        int nightWorkTime = 0;
        double nightWorkMultiple = attendanceWageMultiple.getNightWorkMultiple();
        int nightWorkWage = 0;

        int holidayWorkTime = 0;
        double holidayWorkMultiple = attendanceWageMultiple.getHolidayWorkMultiple();
        int holidayWorkWage = 0;

        int totalSalary = 0;

        for (Attendance attendance : attendanceList) {
            LocalTime startTime = attendance.getStartTime().toLocalTime();
            LocalTime endTime = attendance.getEndTime().toLocalTime();
            DayOfWeek dayOfWeek = attendance.getDayOfWeek();

            System.out.println("ATTENDANCE : " + attendance.getAttendanceDate() + " || " + attendance.getDayOfWeek());

            //System.out.println("DATE : " + attendance.getAttendanceDate() + " DAY OF WEEK " + dayOfWeek);

            if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                //System.out.print("WEEKEND!");
                int diffTime = (int) ChronoUnit.HOURS.between(startTime, endTime);
                holidayWorkTime += diffTime;
                holidayWorkWage += diffTime * attendance.getWage() * holidayWorkMultiple;
                //System.out.println("  ADD " + diffTime);

                continue;
            }

            if (startTime.getHour() < 17) {
                //System.out.print("WORK TIME!");
                int diffTime = (int) ChronoUnit.HOURS.between(startTime, endWorkTime);
                workTime += diffTime;
                workWage += diffTime * attendance.getWage();
                //System.out.println("  ADD " + diffTime);
            }

            //System.out.print(" OVER WORK TIME!");
            if (endTime.getHour() < 20) {
                int diffTime = (int) ChronoUnit.HOURS.between(endWorkTime, endTime);
                overWorkTime += diffTime;
                overWorkWage += diffTime * attendance.getWage() * overWorkMultiple;
                //System.out.println(" ADD " + diffTime);
            } else {
                int diffTime = 3;
                overWorkTime += diffTime;
                overWorkWage += diffTime * attendance.getWage() * overWorkMultiple;
                //System.out.println(" ADD " + diffTime);
            }

            if (endTime.getHour() > 20) {
                //System.out.print("NIGHT WORK TIME!");
                int diffTime = (int) ChronoUnit.HOURS.between(endOverWorkTime, endTime);
                nightWorkTime += diffTime;
                nightWorkWage += diffTime * attendance.getWage() * nightWorkMultiple;
                //System.out.println(" ADD " + diffTime);
            }
        }

        totalSalary = workWage + overWorkWage + nightWorkWage + holidayWorkWage;

        return AttendanceWorkSalaryDtoRead.builder()
                .employeePid(employee.getPid())
                .year(year)
                .month(month)
                .workTime(workTime)
                .workWage(workWage)
                .overWorkTime(overWorkTime)
                .overWorkMultiple(overWorkMultiple)
                .overWorkWage(overWorkWage)
                .nightWorkTime(nightWorkTime)
                .nightWorkMultiple(nightWorkMultiple)
                .nightWorkWage(nightWorkWage)
                .holidayWorkTime(holidayWorkTime)
                .holidayWorkMultiple(holidayWorkMultiple)
                .holidayWorkWage(holidayWorkWage)
                .totalSalary(totalSalary)
                .attendanceSalaryPeeDtoRead(taxCalculator(totalSalary))
                .build();
    }

    @Override
    public List<AttendanceWorkSalaryDtoRead> calculateAllWorkSalaryByYearAndMonth(Employee employee, int year, int month, AttendanceWageMultiple attendanceWageMultiple) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            List<Employee> employeeList = employeeRepository.readAll();

            List<AttendanceWorkSalaryDtoRead> result = new ArrayList<>();
            for (Employee currEmployee : employeeList) {
                result.add(calculateWorkSalaryByEmployeeAndYearAndMonth(currEmployee, year, month, attendanceWageMultiple));
            }
            return result;
        } else {
            throw new RuntimeException();
        }
    }

    public static AttendanceSalaryPeeDtoRead taxCalculator(int input_Tax) {
        //1. 국민 연금 공제
        //총 월급의 9%
        //본인 부담 4.5%, 사업주 부담 4.5%
        //결국 자기가 부담하는 금액은 4.5%임

        long final_Tax_1;
        long tax1_2;
        int total_tax_1;
        if(input_Tax < 370000) { // 세금 공제를 위한 소득월액이 최저 37만원보다 작은 경우
            final_Tax_1 = 370000;
        } else if (input_Tax > 5530000) { // 세금 공제를 위한 소득 월액이 최고 590만원보다 큰 경우
            final_Tax_1 = 5530000;
        } else {
            final_Tax_1 = input_Tax - input_Tax % 1000; // 100원 단위 삭제
        }

        int tax1_1 = (int) ((final_Tax_1 * 45)/1000); // 100원 단위 절삭
        tax1_1 -= tax1_1 % 10; //1원 단위 절삭
        //System.out.println("본인 부담 국민연금 : " + tax1_1);

        // 사업주가 4.5% 부담
        tax1_2 = (final_Tax_1 * 45)/1000;
        tax1_2 -=  tax1_2 % 10 ;
        total_tax_1 = (int) tax1_1 + (int) tax1_2;

        //System.out.println("총 국민연금 공제 : " + total_tax_1); // << 계산용으로 일단 냅둠 아마도 실 세금 내역서에는 아마 4.5%로 뜰거같음

        //2. 국민 건강보험 공제
        //계산기 상 최저 소득이 280000원 아래는 전부 동일한 결과값이 나옴
        //최대는 110,332,016원 이상으로는 전부 동일한 결과값이 나옴
        //이것도 원단위 결삭 해야하는듯?

        long final_Tax_2 = 0;
        int total_tax_2;

        if(input_Tax < 280000) {
            total_tax_2 = 19780;
        } else if (input_Tax > 110332016) {
            total_tax_2 = 7822540;
        } else {
            final_Tax_2 = input_Tax;
        }

        double tax2 = (final_Tax_2 * 709)/10000; //총 국민 건강보험료

        double temp = tax2 / 2;
        int tax2_1 = (int) (Math.floor(temp / 10) * 10);

        //double last_won = tax2_1 % 10; // 일의 자리 찾은 후
        //tax2 = tax2 - last_won; //원단위 삭제

        // 근로자 부담금
        double tax2_2 = tax2 / 2; // 사업자 부담금
        tax2_2 = Math.floor(tax2_2 / 10) * 10;


        total_tax_2 = (int) tax2_1 + (int) tax2_2;

        //System.out.println("본인 부담 건강보험료 : " + tax2_1);
        //System.out.println("총 건강보험료 공제 : " + total_tax_2);

        //3. 국민 건강보험 (장기요양) 공제 << 2의 세금에서 이어짐
        int tax3_1 = 0;
        double tax3_2 = 0;
        int total_Tax_3 = 0;

        double tax3 = (tax2_1 * 1281) / 10000; // 본인 장기 건강보험료, 사업자도 동일함 (1단위 포함)

        tax3_1 = (int) (Math.floor(tax3 / 10) * 10); // 원 단위를 뺌으로써 원 단위 절삭, 이거 Math library 가면 내림 있을거 같은데 그거 써도 되고 이렇게 해도 상관없을듯
        //tax3_2 = tax3 - last_won; //
        tax3_2 = Math.floor(tax3 / 10) * 10;
        total_Tax_3 = (int) tax3_1 + (int) tax3_2;
        //System.out.println("본인 부담 장기 건강보험료 : " + tax3_1);
        //System.out.println("총 장기 건강보험료 공제 : " + total_Tax_3);

        //4. 고용보험료 공제
        // 근로자는 무조건 0.9%
        int tax4_1 = 0;
        double tax4_2 = 0;
        long final_Tax_4 = 0;

        final_Tax_4 = input_Tax;
        tax4_1 = (int) ((final_Tax_4 * 9)/1000.0);  //근로자 세금
        //사업자 세금
        int people = 300;

        if(people < 150) {
            tax4_2 = (final_Tax_4 * 115)/10000.0;
        } //else if(people >= 150 || 우선지원대상기업 뭐 라는데 이건 근데 딱히 없어도 될듯? )
        else if(people >= 150 || people < 1000) {
            tax4_2 = (final_Tax_4 * 155)/10000.0;

        } else if(people >= 1000) {
            tax4_2 = (final_Tax_4 * 175)/10000.0;
        } else {

        }
        tax4_1 = Math.round(tax4_1);
        tax4_2 = Math.ceil(tax4_2);
        int total_Tax_4 = (int) tax4_1 + (int) tax4_2;
        //System.out.println("본인 부담 고용보험료 : " + tax4_1);
        //System.out.println("사업자 부담 고용보험료 : " + tax4_2);
        //System.out.println("총 고용보험료 공제 : " + total_Tax_4);


        return AttendanceSalaryPeeDtoRead.builder()
                .nationalPension(tax1_1)
                .healthInsurance(tax2_1)
                .longTermHealthInsurance(tax3_1)
                .employmentInsurance(tax4_1)
                .build();
    }
}
