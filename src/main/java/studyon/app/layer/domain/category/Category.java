package studyon.app.layer.domain.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.lecture_category.LectureCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 강의 카테고리 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true, exclude = {"lectureCategories"})
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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureCategory> lectureCategories = new ArrayList<>();


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

    // 연관 관계 메소드
    public void addLectureCategory(LectureCategory lectureCategory) {
        this.lectureCategories.add(lectureCategory);
        lectureCategory.setCategory(this);
    }

}
