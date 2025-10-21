package studyon.app.layer.domain.lecture_category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.category.Category;
import studyon.app.layer.domain.lecture.Lecture;

/**
 * 강의 - 카테고리 간 매핑 테이블 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@Setter  // 연관 관계 메소드
@DynamicUpdate
@ToString(callSuper = true, exclude = {"lecture"})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureCategoryId;

    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @Builder
    public LectureCategory(Lecture lecture, Category category) {
        this.lecture = lecture;
        this.category = category;
    }
}
