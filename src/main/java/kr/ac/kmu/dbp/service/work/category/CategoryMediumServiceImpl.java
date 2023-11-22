package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoUpdate;
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
public class CategoryMediumServiceImpl implements CategoryMediumService {
    private final CategoryMediumRepository categoryMediumRepository;
    private final CategoryLargeRepository categoryLargeRepository;

    @Autowired
    public CategoryMediumServiceImpl(CategoryMediumDataBaseRepository categoryMediumDataBaseRepository, CategoryLargeDataBaseRepository categoryLargeDataBaseRepository) {
        this.categoryMediumRepository = categoryMediumDataBaseRepository;
        this.categoryLargeRepository = categoryLargeDataBaseRepository;
    }

    @Override
    public void create(Employee employee, CategoryMediumDtoCreate categoryMediumDtoCreate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategoryMedium categoryMedium = CategoryMedium.builder()
                    .name(categoryMediumDtoCreate.getName())
                    .categoryLarge(categoryLargeRepository.readByPid(categoryMediumDtoCreate.getCategoryLargePid()))
                    .build();
            categoryMediumRepository.create(categoryMedium);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Employee employee, CategoryMediumDtoUpdate categoryMediumDtoUpdate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategoryMedium categoryMedium = CategoryMedium.builder()
                    .pid(categoryMediumDtoUpdate.getPid())
                    .name(categoryMediumDtoUpdate.getName())
                    .categoryLarge(categoryLargeRepository.readByPid(categoryMediumDtoUpdate.getCategoryLargePid()))
                    .build();
            categoryMediumRepository.update(categoryMedium);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Employee employee, int pid) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategoryMedium categoryMedium = CategoryMedium.builder()
                    .pid(pid)
                    .build();
            categoryMediumRepository.delete(categoryMedium);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategoryMediumDtoRead> readAll() {
        List<CategoryMediumDtoRead> result = new ArrayList<>();

        List<CategoryMedium> categoryMediumList = categoryMediumRepository.readAll();

        for (CategoryMedium categoryMedium : categoryMediumList) {
            result.add(new CategoryMediumDtoRead(categoryMedium));
        }

        return result;
    }

    @Override
    public List<CategoryMediumDtoRead> readByCategoryLargePid(int categoryLargePid) {
        CategoryLarge categoryLarge = categoryLargeRepository.readByPid(categoryLargePid);

        List<CategoryMediumDtoRead> result = new ArrayList<>();

        List<CategoryMedium> categoryMediumList = categoryMediumRepository.readByCategoryLarge(categoryLarge);

        for (CategoryMedium categoryMedium : categoryMediumList) {
            result.add(new CategoryMediumDtoRead(categoryMedium));
        }

        return result;
    }
}
