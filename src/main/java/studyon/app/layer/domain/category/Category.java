package studyon.app.layer.domain.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;

/**
 * 강의 카테고리 엔티티 클래스
 * @version 1.0
 * @author khj00
 */

@Entity
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;


    public Category(String name, Category parent, Lecture lecture) {
        this.name = name;
        this.parent = parent;
        this.lecture = lecture;
    }
    
    /*
        카테고리 이름 업데이트 로직
     */

    public void updateName(String name) {
        this.name = name;
    }
}
