package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
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

    @Override
    public void create(Employee employee, CategoryLargeDtoCreate categoryLargeDtoCreate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            categoryLargeRepository.create(new CategoryLarge(categoryLargeDtoCreate));
        } else {
            throw new RuntimeException();
        }
    }
}
