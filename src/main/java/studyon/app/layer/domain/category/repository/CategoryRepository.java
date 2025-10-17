package studyon.app.layer.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studyon.app.layer.domain.category.Category;

import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-17) : khj00 최초 작성
 */

/**
 * 강의 카테고리 레포지토리 인터페이스
 * @version 1.0
 * @author khj00
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
