package kr.ac.kmu.dbp.service.work.category;

import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoCreate;
import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoRead;
import kr.ac.kmu.dbp.dto.work.category.CategorySmallDtoUpdate;
import kr.ac.kmu.dbp.entity.employee.Employee;
import kr.ac.kmu.dbp.entity.employee.Role;
import kr.ac.kmu.dbp.entity.work.category.CategoryMedium;
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

        init();
    }


    private void init() {

        String[] 인사_정기 = new String[] {"근퇴 확인", "급여 중간 계산"};
        String[] 인사_단기 = new String[] {"사무실 청소"};
        String[] 개발_정기 = new String[] {"일간 회의 진행", "개발"};
        String[] 개발_단기 = new String[] {"사무실 청소"};
        String[] 영업_정기 = new String[] {"고객사 방문", "영업 이익 중간 정산"};
        String[] 영업_단기 = new String[] {"사무실 청소"};

        String[][] 인사 = new String[][] {인사_정기, 인사_단기};
        String[][] 개발 = new String[][] {개발_정기, 개발_단기};
        String[][] 영업 = new String[][] {영업_정기, 영업_단기};

        String[][][] categoryMediumList = new String[][][] {인사, 개발, 영업};

        int count = 0;
        for (int T = 0 ; T < categoryMediumList.length ; T ++) {
            String[][] tempI = categoryMediumList[T];
            for (int TI = 0 ; TI < tempI.length ; TI ++) {
                String[] tempII = tempI[TI];
                count ++;
                CategoryMedium categoryMedium = categoryMediumRepository.readByPid(count);
                for (int TII = 0 ; TII < tempII.length ; TII ++) {
                    if (!categorySmallRepository.checkExistByCategoryMediumPidAndName(categoryMedium.getPid(), categoryMediumList[T][TI][TII])) {
                        categorySmallRepository.create(CategorySmall.builder().categoryMedium(categoryMedium).name(categoryMediumList[T][TI][TII]).build());
                    }
                }
            }
        }
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

    @Override
    public List<CategorySmallDtoRead> readByCategoryMediumPid(int categoryMediumPid) {
        CategoryMedium categoryMedium = categoryMediumRepository.readByPid(categoryMediumPid);

        List<CategorySmallDtoRead> result = new ArrayList<>();

        List<CategorySmall> categorySmallList = categorySmallRepository.readByCategoryMedium(categoryMedium);

        for (CategorySmall categorySmall : categorySmallList) {
            result.add(new CategorySmallDtoRead(categorySmall));
        }

        return result;
    }

    @Override
    public CategorySmallDtoRead readByPid(int pid) {
        return new CategorySmallDtoRead(categorySmallRepository.readByPid(pid));
    }
}
