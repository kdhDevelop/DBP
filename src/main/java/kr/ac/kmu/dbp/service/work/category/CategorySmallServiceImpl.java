package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.repository.work.category.CategorySmallDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorySmallServiceImpl implements CategorySmallService {
    private final CategorySmallRepository categorySmallRepository;

    @Autowired
    public CategorySmallServiceImpl(CategorySmallDataBaseRepository categorySmallDataBaseRepository) {
        this.categorySmallRepository = categorySmallDataBaseRepository;
    }
}
