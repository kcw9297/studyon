package studyon.app.layer.domain.lecture_like;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import studyon.app.layer.base.entity.BaseEntity;
import studyon.app.layer.domain.lecture.Lecture;
import studyon.app.layer.domain.member.Member;

/**
 * 강의 좋아요(찜) 클래스
 * @version 1.0
 * @author khj00
 */

// 강의 중복 좋아요를 막기 위한 UK 설정
@Table(
        name = "lecture_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "lecture_id"})
        }
)
@Entity
@Getter
@DynamicUpdate
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;


    @Builder
    public LectureLike(Member member, Lecture lecture) {
        this.member = member;
        this.lecture = lecture;
    }
}
