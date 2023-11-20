package kr.ac.kmu.dbp.service.work;

import kr.ac.kmu.dbp.repository.work.WorkEnrollDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.WorkEnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkEnrollServiceImpl implements WorkEnrollService {
    private final WorkEnrollRepository workEnrollRepository;

    @Autowired
    public WorkEnrollServiceImpl(WorkEnrollDataBaseRepository workEnrollDataBaseRepository) {
        this.workEnrollRepository = workEnrollDataBaseRepository;
    }
}
