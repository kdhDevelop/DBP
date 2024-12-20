package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategoryMediumDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.category.CategoryLarge;
import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import kr.ac.kmu.dbp.repository.work.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryMediumServiceImpl implements CategoryMediumService {
    private final CategoryMediumRepository categoryMediumRepository;
    private final CategoryLargeRepository categoryLargeRepository;
    private final CategorySmallRepository categorySmallRepository;

    @Autowired
    public CategoryMediumServiceImpl(CategoryMediumDataBaseRepository categoryMediumDataBaseRepository, CategoryLargeDataBaseRepository categoryLargeDataBaseRepository, CategorySmallDataBaseRepository categorySmallDataBaseRepository) {
        this.categorySmallRepository = categorySmallDataBaseRepository;
        this.categoryMediumRepository = categoryMediumDataBaseRepository;
        this.categoryLargeRepository = categoryLargeDataBaseRepository;

        //init();
    }

    private void init() {
        String[] categoryMediumNameList = new String[] {"정기 업무", "단기 업무"};
        for (int T = 1 ; T < 4 ; T ++) {
            CategoryLarge categoryLarge = categoryLargeRepository.readByPid(T);
            for (int TI = 0 ; TI < categoryMediumNameList.length ; TI ++) {
                if (!categoryMediumRepository.checkExistByCategoryLargePidAndName(T, categoryMediumNameList[TI])) {
                    categoryMediumRepository.create(CategoryMedium.builder().categoryLarge(categoryLarge).name(categoryMediumNameList[TI]).build());
                }
            }
        }
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
            CategoryMedium categoryMedium = categoryMediumRepository.readByPid(pid);
            categoryMediumRepository.delete(categoryMedium);
            List<CategorySmall> categorySmallList = categorySmallRepository.readByCategoryMedium(categoryMedium);
            for (CategorySmall categorySmall : categorySmallList) {
                categorySmallRepository.delete(categorySmall);
            }
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

    @Override
    public CategoryMediumDtoRead readByPid(int pid) {
        return new CategoryMediumDtoRead(categoryMediumRepository.readByPid(pid));
    }
}
