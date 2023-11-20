package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.category.CategorySmall;
import kr.ac.kmu.dbp.repository.work.category.CategoryMediumDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategoryMediumRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallDataBaseRepository;
import kr.ac.kmu.dbp.repository.work.category.CategorySmallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorySmallServiceImpl implements CategorySmallService {
    private final CategorySmallRepository categorySmallRepository;
    private final CategoryMediumRepository categoryMediumRepository;

    @Autowired
    public CategorySmallServiceImpl(CategorySmallDataBaseRepository categorySmallDataBaseRepository, CategoryMediumDataBaseRepository categoryMediumDataBaseRepository) {
        this.categorySmallRepository = categorySmallDataBaseRepository;
        this.categoryMediumRepository = categoryMediumDataBaseRepository;
    }

    @Override
    public void create(Employee employee, CategorySmallDtoCreate categorySmallDtoCreate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategorySmall categorySmall = CategorySmall.builder()
                    .name(categorySmallDtoCreate.getName())
                    .categoryMedium(categoryMediumRepository.readByPid(categorySmallDtoCreate.getCategoryMediumPid()))
                    .build();
            categorySmallRepository.create(categorySmall);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Employee employee, CategorySmallDtoUpdate categorySmallDtoUpdate) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategorySmall categorySmall = CategorySmall.builder()
                    .pid(categorySmallDtoUpdate.getPid())
                    .name(categorySmallDtoUpdate.getName())
                    .categoryMedium(categoryMediumRepository.readByPid(categorySmallDtoUpdate.getCategoryMediumPid()))
                    .build();
            categorySmallRepository.update(categorySmall);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Employee employee, int pid) {
        if (employee.getRole() == Role.부서장 || employee.getRole() == Role.사장) {
            CategorySmall categorySmall = CategorySmall.builder()
                    .pid(pid)
                    .build();
            categorySmallRepository.delete(categorySmall);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategorySmallDtoRead> readAll() {
        List<CategorySmallDtoRead> result = new ArrayList<>();

        List<CategorySmall> categorySmallList = categorySmallRepository.readAll();

        for (CategorySmall categorySmall : categorySmallList) {
            result.add(new CategorySmallDtoRead(categorySmall));
        }

        return result;
    }
}
