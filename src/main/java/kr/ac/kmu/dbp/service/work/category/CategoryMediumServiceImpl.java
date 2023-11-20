package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.repository.work.category.CategoryMediumDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryMediumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMediumServiceImpl implements CategoryMediumService {
    private final CategoryMediumRepository categoryMediumRepository;

    @Autowired
    public CategoryMediumServiceImpl(CategoryMediumDataBaseRepository categoryMediumDataBaseRepository) {
        this.categoryMediumRepository = categoryMediumDataBaseRepository;
    }
}
