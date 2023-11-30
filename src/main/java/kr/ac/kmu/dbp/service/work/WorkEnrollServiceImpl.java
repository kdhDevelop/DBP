package kr.ac.kmu.dbp.service.work;

import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoCreate;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoRead;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.WorkEnroll;
import kr.ac.kmu.dbp.repository.employee.EmployeeDataBaseRepository;
import kr.ac.kmu.dbp.repository.employee.EmployeeRepository;
import kr.ac.kmu.dbp.repository.work.WorkEnrollDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.WorkEnrollRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkEnrollServiceImpl implements WorkEnrollService {
    private final WorkEnrollRepository workEnrollRepository;
    private final CategorySmallRepository categorySmallRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public WorkEnrollServiceImpl(WorkEnrollDataBaseRepository workEnrollDataBaseRepository, CategorySmallDataBaseRepository categorySmallDataBaseRepository, EmployeeDataBaseRepository employeeDataBaseRepository) {
        this.workEnrollRepository = workEnrollDataBaseRepository;
        this.categorySmallRepository = categorySmallDataBaseRepository;
        this.employeeRepository = employeeDataBaseRepository;
    }


    private boolean validationTime(WorkEnroll workEnroll) {
        Time startTimeNewWork = workEnroll.getStartWork();
        Time endTimeNewWork = workEnroll.getEndWork();

        //startTimeNewWork 보다 endTimeNewWork 가 뒤에 있는 경우 true
        if (startTimeNewWork.after(endTimeNewWork)) {
            throw new RuntimeException();
        } else {
            System.out.println("CHECK. END TIME > START TIME");
        }

        List<WorkEnroll> workEnrollList = workEnrollRepository.readByEmployeeAndDateOrderByStartWork(workEnroll.getEmployee(), workEnroll.getWorkDate());

        if (!workEnrollList.isEmpty()) {
            System.out.println("WORK ENROLL LIST IS NOT EMPTY");
            for (int T = workEnrollList.size() - 1 ; T >= 0 ; T --) {
                WorkEnroll beforeWorkEnroll = workEnrollList.get(T);
                if (beforeWorkEnroll.getEndWork().before(startTimeNewWork) || beforeWorkEnroll.getEndWork().equals(startTimeNewWork)) {
                    if (T == workEnrollList.size() - 1) {
                        return true;
                    }
                    WorkEnroll afterWorkEnroll = workEnrollList.get(T + 1);
                    if (afterWorkEnroll.getStartWork().after(endTimeNewWork) || afterWorkEnroll.getStartWork().equals(endTimeNewWork)) {
                        return true;
                    }
                }
            }
            WorkEnroll afterWorkEnroll = workEnrollList.get(0);
            if (afterWorkEnroll.getStartWork().after(endTimeNewWork) || afterWorkEnroll.getStartWork().equals(endTimeNewWork)) {
                return true;
            }
        } else {
            System.out.println("WORK ENROLL LIST IS EMPTY RETURN TRUE.");
            return true;
        }

        return false;
    }

    @Override
    public void create(Employee employee, WorkEnrollDtoCreate workEnrollDtoCreate) {

        System.out.println("EMPLOYEE : " + employee.getPid());
        System.out.println("DATE : " + workEnrollDtoCreate.getWorkDate().toString());
        System.out.println("CATEGORY : " + workEnrollDtoCreate.getCategorySmallPid());
        System.out.println("START : " + workEnrollDtoCreate.getStartWork());
        System.out.println("END : " + workEnrollDtoCreate.getEndWork());


        WorkEnroll workEnroll = WorkEnroll.builder()
                .employee(employee)
                .workDate(workEnrollDtoCreate.getWorkDate())
                .categorySmall(categorySmallRepository.readByPid(workEnrollDtoCreate.getCategorySmallPid()))
                .startWork(workEnrollDtoCreate.getStartWork())
                .endWork(workEnrollDtoCreate.getEndWork())
                .build();

        if (validationTime(workEnroll))
            workEnrollRepository.create(workEnroll);
        else
            throw new RuntimeException();
    }

    @Override
    public void update(Employee employee, WorkEnrollDtoUpdate workEnrollDtoUpdate) {
        if (employee.getRole() == Role.사장 || employee.getRole() == Role.부서장) {
            WorkEnroll workEnroll = WorkEnroll.builder()
                    .pid(workEnrollDtoUpdate.getPid())
                    .employee(employee)
                    .workDate(workEnrollDtoUpdate.getWorkDate())
                    .categorySmall(categorySmallRepository.readByPid(workEnrollDtoUpdate.getPid()))
                    .startWork(workEnrollDtoUpdate.getStartWork())
                    .endWork(workEnrollDtoUpdate.getEndWork())
                    .build();

            if (validationTime(workEnroll))
                workEnrollRepository.update(workEnroll);
            else
                throw new RuntimeException();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Employee employee, int pid) {
        if (employee.getRole() == Role.사장 || employee.getRole() == Role.부서장) {
            WorkEnroll workEnroll = WorkEnroll.builder()
                    .pid(pid)
                    .build();
            workEnrollRepository.delete(workEnroll);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<WorkEnrollDtoRead> readAll(Employee employee, Date date) {
        List<WorkEnrollDtoRead> result = new ArrayList<>();
        List<WorkEnroll> workEnrollList = workEnrollRepository.readByEmployeeAndDateOrderByStartWork(employee, date);

        for (WorkEnroll workEnroll : workEnrollList) {
            result.add(new WorkEnrollDtoRead(workEnroll));
        }

        return result;
    }

    @Override
    public List<WorkEnrollDtoRead> searchByDateAndCategorySmallPid(Employee lookUpEmployee, Date date, int categorySmallPid) {
        if (lookUpEmployee.getRole() == Role.부서장 || lookUpEmployee.getRole() == Role.사장) {
            List<WorkEnrollDtoRead> result = new ArrayList<>();
            List<WorkEnroll> workEnrollList = workEnrollRepository.readByDateAndCategorySmall(date, categorySmallRepository.readByPid(categorySmallPid));
            for (WorkEnroll workEnroll : workEnrollList) {
                result.add(new WorkEnrollDtoRead(workEnroll));
            }
            return result;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<WorkEnrollDtoRead> searchByEmployeePid(Employee lookUpEmployee, int employeePid) {
        if (lookUpEmployee.getRole() == Role.부서장 || lookUpEmployee.getRole() == Role.사장) {
            List<WorkEnrollDtoRead> result = new ArrayList<>();
            List<WorkEnroll> workEnrollList = workEnrollRepository.readByEmployee(employeeRepository.readByPid(employeePid));
            for (WorkEnroll workEnroll : workEnrollList) {
                result.add(new WorkEnrollDtoRead(workEnroll));
            }
            return result;
        } else {
            throw new RuntimeException();
        }
    }
}
