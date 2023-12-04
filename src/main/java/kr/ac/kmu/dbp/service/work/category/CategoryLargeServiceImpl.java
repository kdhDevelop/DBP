package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategoryLargeDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
import kr.ac.kmu.dbp.repository.work.category.CategoryLargeDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryLargeRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryMediumDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryMediumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryLargeServiceImpl implements CategoryLargeService {
    private final CategoryLargeRepository categoryLargeRepository;
    private final CategoryMediumRepository categoryMediumRepository;

    @Autowired
    public CategoryLargeServiceImpl(CategoryLargeDataBaseRepository categoryLargeDataBaseRepository, CategoryMediumDataBaseRepository categoryMediumDataBaseRepository) {
        this.categoryLargeRepository = categoryLargeDataBaseRepository;
        this.categoryMediumRepository = categoryMediumDataBaseRepository;

        //init();
    }

    private void init() {
        String[] departmentNameList = new String[] {"인사", "개발", "영업"};

        for (String departmentName : departmentNameList) {
            if (!categoryLargeRepository.checkExistByName(departmentName)) {
                categoryLargeRepository.create(CategoryLarge.builder().name(departmentName).build());
            }
        }
    }

    @Override
    public void create(Employee employee, CategoryLargeDtoCreate categoryLargeDtoCreate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            categoryLargeRepository.create(new CategoryLarge(categoryLargeDtoCreate));
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Employee employee, CategoryLargeDtoUpdate categoryLargeDtoUpdate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            categoryLargeRepository.update(new CategoryLarge(categoryLargeDtoUpdate));
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Employee employee, int pid) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategoryLarge categoryLarge = categoryLargeRepository.readByPid(pid);
            categoryLargeRepository.delete(categoryLarge);
            List<CategoryMedium> categoryMediumList = categoryMediumRepository.readByCategoryLarge(categoryLarge);
            for (CategoryMedium categoryMedium : categoryMediumList) {
                categoryMediumRepository.delete(categoryMedium);
            }
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategoryLargeDtoRead> readAll() {
        List<CategoryLargeDtoRead> result = new ArrayList<>();

        List<CategoryLarge> categoryLargeList = categoryLargeRepository.readAll();

        for (CategoryLarge categoryLarge : categoryLargeList) {
            result.add(new CategoryLargeDtoRead(categoryLarge));
        }

        return result;
    }
    @Override
    public CategoryLargeDtoRead readByPid(int pid) {
        return new CategoryLargeDtoRead(categoryLargeRepository.readByPid(pid));
    }
}
