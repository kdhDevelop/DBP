package kr.ac.kmu.dbp.service.work;

import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoCreate;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoRead;
import kr.ac.kmu.dbp.dto.work.WorkEnrollDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.work.WorkEnroll;
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

    @Autowired
    public WorkEnrollServiceImpl(WorkEnrollDataBaseRepository workEnrollDataBaseRepository, CategorySmallDataBaseRepository categorySmallDataBaseRepository) {
        this.workEnrollRepository = workEnrollDataBaseRepository;
        this.categorySmallRepository = categorySmallDataBaseRepository;
    }


    private boolean validationTime(WorkEnroll workEnroll) {
        Time startTimeNewWork = workEnroll.getStartWork();
        Time endTimeNewWork = workEnroll.getEndWork();

        //startTimeNewWork 보다 endTimeNewWork 가 뒤에 있는 경우 true
        if (startTimeNewWork.after(endTimeNewWork)) {
            throw new RuntimeException();
        }

        List<WorkEnroll> workEnrollList = workEnrollRepository.readByEmployeeAndDateOrderByStartWork(workEnroll.getEmployee(), workEnroll.getWorkDate());

        if (!workEnrollList.isEmpty()) {
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
            return true;
        }

        return false;
    }

    @Override
    public void create(Employee employee, WorkEnrollDtoCreate workEnrollDtoCreate) {
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
    }

    @Override
    public void delete(Employee employee, int pid) {
        WorkEnroll workEnroll = WorkEnroll.builder()
                .pid(pid)
                .build();
        workEnrollRepository.delete(workEnroll);
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
}
