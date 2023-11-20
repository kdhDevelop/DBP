package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.repository.work.category.CategoryLargeDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryLargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryLargeServiceImpl implements CategoryLargeService {
    private final CategoryLargeRepository categoryLargeRepository;

    @Autowired
    public CategoryLargeServiceImpl(CategoryLargeDataBaseRepository categoryLargeDataBaseRepository) {
        this.categoryLargeRepository = categoryLargeDataBaseRepository;
    }
}
