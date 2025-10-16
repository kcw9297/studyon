package studyon.app.layer.domain.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_category.LectureCategory;

import java.util.ArrayList;
import java.util.List;

/*
 * [수정 이력]
 *  ▶ ver 1.0 (2025-10-15) : khj00 최초 작성
 */

/**
 * 강의 카테고리 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder
    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }
    
    /*
        카테고리 이름 업데이트 로직
     */

    public void updateName(String name) {
        this.name = name;
    }

}
