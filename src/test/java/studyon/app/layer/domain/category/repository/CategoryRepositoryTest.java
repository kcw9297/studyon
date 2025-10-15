package studyon.app.layer.domain.category.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studyon.app.common.enums.Difficulty;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.lecture.Lecture;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 등록 및 조회 테스트")
    void saveCategory() {
        // given(준비)
        Category category = Category.builder()
                .name("과학")
                .parent(null)
                .build();

        // when(실행)

        Category savedCategory = categoryRepository.save(category);

        // then(검증)

        Category foundCategory = categoryRepository.findById(savedCategory.getCategoryId()).orElseThrow();

        assertThat(foundCategory.getName()).isEqualTo("과학");
        assertThat(foundCategory.getParent()).isNull();
    }

    @Test
    @DisplayName("부모-자식 카테고리 등록 테스트")
    void saveParentChildCategory() {
        // given(준비)
        Category parent = categoryRepository.save(Category.builder()
                .name("과학")
                .build());

        Category child = categoryRepository.save(Category.builder()
                .name("화학")
                .parent(parent)
                .build());

        // when(실행)

        Category foundChild = categoryRepository.findById(child.getCategoryId()).orElseThrow();

        // then(검증)
        assertThat(foundChild.getName()).isEqualTo("화학");
        assertThat(foundChild.getParent().getName()).isEqualTo("과학");
    }

    @Test
    @DisplayName("1차/2차 카테고리 그룹핑 및 출력 테스트")
    void printCategoryHierarchy() {
        // given(준비)
        // 테스트용 데이터 저장중
        Category science = categoryRepository.save(Category.builder().name("과학").parent(null).build());
        Category math = categoryRepository.save(Category.builder().name("수학").parent(null).build());
        Category physics = categoryRepository.save(Category.builder().name("물리학").parent(science).build());
        Category chemistry = categoryRepository.save(Category.builder().name("화학").parent(science).build());
        Category algebra = categoryRepository.save(Category.builder().name("수학1").parent(math).build());
        Category geometry = categoryRepository.save(Category.builder().name("기하").parent(math).build());

        // when(실행)
        List<Category> categories = categoryRepository.findAll();

        // 부모가 있는 2차 카테고리만 grouping
        Map<Category, List<Category>> grouped =
                categories.stream()
                        .filter(c -> c.getParent() != null)
                        .collect(Collectors.groupingBy(Category::getParent));

        // then(검증(출력 확인))
        categories.stream()
                .filter(c -> c.getParent() == null) // 1차 카테고리
                .forEach(parent -> {
                    System.out.println("▶ " + parent.getName());
                    grouped.getOrDefault(parent, List.of())
                            .forEach(child -> System.out.println("  └ " + child.getName()));
                });
    }
}